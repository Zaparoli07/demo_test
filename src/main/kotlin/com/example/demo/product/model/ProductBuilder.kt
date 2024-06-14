package com.example.demo.product.model

import com.example.demo.product.model.Product.ProductExistsException
import java.util.UUID
import java.util.function.Predicate

class ProductBuilder(
    var id: UUID? = null,
    var name: String? = null,
    var description: String? = null,
    var categories: List<Category>? = emptyList(),

    var existsProductConstraint: Predicate<Boolean> = Predicate<Boolean> { it == true }
) {
    fun name(name: String) = apply { this.name = name }

    fun description(description: String) = apply { this.description = description }

    fun categories(categories: List<Category>) = apply { this.categories = categories }

    fun existsProductConstraint(exists: Boolean) = apply {
        if (this.existsProductConstraint.test(exists)) {
            throw ProductExistsException()
        }
    }

    fun build(): Product {
        this.id = UUID.randomUUID()

        return Product(this)
    }
}