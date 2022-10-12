package com.example.demo.app.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(
    val registerProductUseCase: RegisterProductUseCase
) {

    @GetMapping
    fun getProduct(): Product {
        return registerProductUseCase.handle(RegisterProductUseCase.RegisterProduct(
            Product(name = "Teste")
        ))
    }
}
