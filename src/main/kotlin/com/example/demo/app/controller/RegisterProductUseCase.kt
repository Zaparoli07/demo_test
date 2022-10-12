package com.example.demo.app.controller

interface RegisterProductUseCase {

    fun handle(registerProduct: RegisterProduct): Product

    fun on(productRegistered: ProductRegistered)

    data class RegisterProduct(val product: Product)

    data class ProductRegistered(val product: Product)
}