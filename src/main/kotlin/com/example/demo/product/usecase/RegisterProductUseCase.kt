package com.example.demo.product.usecase

import com.example.demo.product.model.Category
import com.example.demo.product.model.Product
import com.example.demo.product.usecase.RegisterProductUseCase.RegisterProduct
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

interface RegisterProductUseCase {

    fun handle(cmd: RegisterProduct): Product
    fun on(event: ProductRegistered)

    class RegisterProduct(
        @field:NotBlank(message = "Name must be informed")
        val name: String,
        @field:NotBlank(message = "Description must be informed")
        val description: String,
        @field:NotNull(message = "Categories must be informed")
        val categories: List<String>
    )

    data class ProductRegistered(val product: Product)
}

fun RegisterProduct.toCategories(): List<Category> {
    return this.categories.map { Category(description = it) }
}
