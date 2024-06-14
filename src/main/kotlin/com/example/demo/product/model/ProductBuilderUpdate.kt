package com.example.demo.product.model;

import com.example.demo.product.model.Product.ProductNotExistsException
import java.util.UUID

class ProductBuilderUpdate(
    var id: UUID? = null,
    var name: String? = null,
    var description: String? = null,
    var existsProductConstraint: Boolean? = false
) {
    fun id(id: UUID) = apply { this.id = id }
    fun name(name: String) = apply { this.name = name }
    fun description(description: String) = apply { this.description = description }
    fun existsProductConstraint(exists: Boolean) = apply { this.existsProductConstraint = exists }

    fun apply(): Product {

        this.applyConstraint()

        return Product(this)
    }

    private fun applyConstraint() {
        if (existsProductConstraint == false) throw ProductNotExistsException()
    }
}
