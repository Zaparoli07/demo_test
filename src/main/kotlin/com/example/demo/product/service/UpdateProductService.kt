package com.example.demo.product.service

import com.example.demo.config.rabbit.Publisher
import com.example.demo.product.model.Product
import com.example.demo.product.model.ProductBuilderUpdate
import com.example.demo.product.repository.ProductRepository
import com.example.demo.product.usecase.UpdateProductUseCase
import com.example.demo.product.usecase.UpdateProductUseCase.ProductUpdated
import com.example.demo.product.usecase.UpdateProductUseCase.UpdateProduct
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UpdateProductService(
    val repository: ProductRepository,
    val publiser: Publisher
) : UpdateProductUseCase {
    override fun handle(id: UUID, cmd: UpdateProduct): Product {
        val product = ProductBuilderUpdate()
            .id(id)
            .name(cmd.name)
            .description(cmd.description)
            .existsProductConstraint(repository.existsById(id))
            .apply()

        return repository.save(product)
    }

    @EventListener
    override fun on(productUpdated: ProductUpdated) {
        publiser.publish(message = productUpdated)
    }
}
