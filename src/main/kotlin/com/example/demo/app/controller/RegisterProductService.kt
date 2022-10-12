package com.example.demo.app.controller

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service


@Service
class RegisterProductService(
    val publiser: ApplicationEventPublisher
): RegisterProductUseCase {
    override fun handle(registerProduct: RegisterProductUseCase.RegisterProduct): Product {
        val product = Product(name = "Teste", description = "Teste")
        product.created()
        return product
    }

    @EventListener
    @Override
    override fun on(productRegistered: RegisterProductUseCase.ProductRegistered) {
        this.publiser.publishEvent(productRegistered)
    }
}