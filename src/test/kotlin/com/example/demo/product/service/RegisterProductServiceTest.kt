package com.example.demo.product.service

import com.example.demo.config.rabbit.Publisher
import com.example.demo.fixture.ProductFixture
import com.example.demo.product.model.Product.ProductExistsException
import com.example.demo.product.repository.ProductRepository
import com.example.demo.product.usecase.RegisterProductUseCase.RegisterProduct
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

internal class RegisterProductServiceTest {
    // Mock do banco de dados ou repositório
    private val repository: ProductRepository = mock(ProductRepository::class.java)
    private val publisher: Publisher = mock(Publisher::class.java)

    // Classe a ser testada com seus MOCKs
    private val service = RegisterProductService(
        repository = repository,
        publisher = publisher
    )

    @Test
    @DisplayName(value = """
        Cenário: Cadastrar Produto Inexistente
        DADO que eu deseje cadastrar um produto
        QUANDO informo os dados de um produto que não está cadastrado
        ENTÃO deve retornar com sucesso o produto cadastrado
    """)
    fun deveSalvarProdutoComSucesso() {
        // DADO - Dados do Produto
        val expect = ProductFixture.get()

        val cmd = RegisterProduct(
            name = "XPTO",
            description = "XPTO",
            categories = listOf("XPTO")
        )

        // QUANDO - MOCK do objeto simulado do banco de dados inexistente
        `when`(repository.existsByName(cmd.name)).thenReturn(false)
        `when`(repository.save(any())).thenReturn(expect)

        // ENTÃO - Afirma o objeto de produto foi retornado do método
        assertEquals(expect, service.handle(cmd))
    }

    @Test
    @DisplayName(value = """
        Cenário: Cadastrar Produto Existente
        DADO que eu deseje cadastrar um produto
        QUANDO informo os dados de um produto que está cadastrado
        ENTÃO deve retornar uma exceção de produto já cadastrado
    """)
    fun naoDeveSalvarProdutoExistente() {
        // DADO - Dados do Produto
        val cmd = RegisterProduct(
            name = "XPTO",
            description = "XPTO",
            categories = listOf("XPTO")
        )

        // QUANDO - MOCK do objeto simulado existente do banco de dados
        `when`(repository.existsByName(cmd.name)).thenReturn(true)

        // ENTÃO - Afirma que a exceção foi disparada
        assertThrows<ProductExistsException> {
            service.handle(cmd)
        }
    }
}
