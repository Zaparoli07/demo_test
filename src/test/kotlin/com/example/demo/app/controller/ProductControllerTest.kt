package com.example.demo.app.controller

import com.example.demo.product.usecase.RegisterProductUseCase.RegisterProduct
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@TestMethodOrder(OrderAnnotation::class)
internal class ProductControllerTest : IntegrationTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @Order(1)
    @DisplayName(
        value = """
        Cenário: Cadastrar Produto Inexistente
        DADO que eu deseje cadastrar um produto
        QUANDO informo os dados de um produto que não está cadastrado
        ENTÃO deve retornar com sucesso o produto cadastrado
    """
    )
    fun deveSalvarProdutoComSucesso() {
        // Realiza requisição e afirma o retorno de sucesso e as informações do produto
        mockMvc.perform(
            post("/product")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    jacksonObjectMapper().writeValueAsString(
                        RegisterProduct(
                            name = "Nintendo Switch",
                            description = "Console Nintendo Switch",
                            categories = listOf("videogame")
                        )
                    )
                )
        )
            // Oque é esperado
            .andExpect(status().isOk) // Status de sucesso
            .andExpect(jsonPath("\$.name").value("Nintendo Switch")) // Nome do Produto
            .andExpect(jsonPath("\$.description").value("Console Nintendo Switch")) // Descrição do Produto
    }

    @Test
    @Order(2)
    @DisplayName(
        value = """
        Cenário: Cadastrar Produto Existente
        DADO que eu deseje cadastrar um produto
        QUANDO informo os dados de um produto que está cadastrado
        ENTÃO deve retornar uma exceção de produto já cadastrado
    """
    )
    @Sql("/sql/product.sql")
    fun naoDeveSalvarProdutoExistente() {
        // Realiza requisição e afirma o retorno de conflito com código e mensagem de erro
        mockMvc.perform(
            post("/product")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    jacksonObjectMapper().writeValueAsString(
                        RegisterProduct(
                            name = "Nintendo Switch",
                            description = "Console Nintendo Switch",
                            categories = listOf("videogame")
                        )
                    )
                )
        )
            // Oque é esperado
            .andExpect(status().isConflict) // Status de erro
            .andExpect(jsonPath("\$.code").value("409")) // Código de Erro
            .andExpect(jsonPath("\$.message").value("Product already exists")) // Mensagem de Erro
    }
}
