package com.example.demo.fixture

import com.example.demo.product.model.Category
import com.example.demo.product.model.Product
import java.util.UUID

object ProductFixture {
    fun get(): Product {
        return Product(
            id = UUID.randomUUID(), name = "XPTO", description = "XPTO", categories = listOf(
                Category(id = UUID.randomUUID(), description = "XPTO")
            )
        )
    }
}
