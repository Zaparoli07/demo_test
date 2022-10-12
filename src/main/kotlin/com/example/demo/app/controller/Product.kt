package com.example.demo.app.controller

import com.example.demo.app.controller.RegisterProductUseCase.*
import org.springframework.data.domain.AbstractAggregateRoot

data class Product(

    val name: String? = null,

    val description: String? = null

): AbstractAggregateRoot<Product>() {

    fun created() {
        registerEvent(ProductRegistered(this))
    }
}
