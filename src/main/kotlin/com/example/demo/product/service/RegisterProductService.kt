package com.example.demo.product.service

import com.example.demo.config.rabbit.Publisher
import com.example.demo.product.model.Category
import com.example.demo.product.model.Product
import com.example.demo.product.model.ProductBuilder
import com.example.demo.product.repository.ProductRepository
import com.example.demo.product.usecase.RegisterProductUseCase
import com.example.demo.product.usecase.RegisterProductUseCase.ProductRegistered
import com.example.demo.product.usecase.RegisterProductUseCase.RegisterProduct
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class RegisterProductService(
    val repository: ProductRepository,
    val publisher: Publisher
) : RegisterProductUseCase {
    override fun handle(cmd: RegisterProduct): Product {
        val product = ProductBuilder()
            .existsProductConstraint(repository.existsByName(cmd.name))
            .name(cmd.name)
            .description(cmd.description)
            .categories(cmd.categories.map { Category(description = it) })
            .build()

        return repository.save(product)
    }

    @EventListener
    override fun on(event: ProductRegistered) {
        publisher.publish(message = event)
    }
}
