package com.example.demo.product.repository

import com.example.demo.product.model.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Repository
interface ProductRepository : CrudRepository<Product, UUID> {

    fun existsByName(name: String): Boolean

    fun getById(id: UUID): Product {
        return this.findById(id).orElseThrow { ProductNotFoundException() }
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ProductNotFoundException : RuntimeException("Product not found")
