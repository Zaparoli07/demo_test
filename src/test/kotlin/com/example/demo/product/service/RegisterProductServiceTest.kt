package com.example.demo.product.service

import com.example.demo.config.rabbit.Publisher
import com.example.demo.fixture.ProductFixture
import com.example.demo.product.model.Product.ProductExistsException
import com.example.demo.product.repository.ProductRepository
import com.example.demo.product.usecase.RegisterProductUseCase.RegisterProduct
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

internal class RegisterProductServiceTest {
    private val repository: ProductRepository = mock(ProductRepository::class.java)
    private val publisher: Publisher = mock(Publisher::class.java)

    private val service = RegisterProductService(
        repository = repository,
        publisher = publisher
    )

    @Test
    fun `should save product`() {
        //Given
        val expect = ProductFixture.get()

        val cmd = RegisterProduct(
            name = "XPTO",
            description = "XPTO",
            categories = listOf("XPTO")
        )

        //When
        `when`(repository.existsByName(cmd.name)).thenReturn(false)
        `when`(repository.save(any())).thenReturn(expect)

        //Then
        assertEquals(expect, service.handle(cmd))
    }

    @Test
    fun `should throws ProductExistsException when exists product`() {
        //Given
        val cmd = RegisterProduct(
            name = "XPTO",
            description = "XPTO",
            categories = listOf("XPTO")
        )

        //When
        `when`(repository.existsByName(cmd.name)).thenReturn(true)

        //Then
        assertThrows<ProductExistsException> {
            service.handle(cmd)
        }
    }
}
