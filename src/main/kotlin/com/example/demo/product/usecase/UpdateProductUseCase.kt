package com.example.demo.product.usecase

import com.example.demo.product.model.Product
import java.util.UUID
import javax.validation.constraints.NotBlank

interface UpdateProductUseCase {

    fun handle(id: UUID, cmd: UpdateProduct): Product

    fun on(productUpdated: ProductUpdated)

    data class UpdateProduct(
        @NotBlank(message = "Name must be informed")
        val name: String,
        @NotBlank(message = "Description must be informed")
        val description: String,
    )

    data class ProductUpdated(val product: Product)
}
