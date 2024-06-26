package com.example.demo.app.controller

import com.example.demo.product.usecase.RegisterProductUseCase.RegisterProduct
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

internal class ProductControllerTest : IntegrationTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `happy way`() {
        val cmd = RegisterProduct(
            name = "Nintendo Switch",
            description = "Console Nintendo Switch",
            categories = listOf("videogame")
        )

        mockMvc.perform(
            post("/product")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jacksonObjectMapper().writeValueAsString(cmd))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("\$.name").value("Nintendo Switch"))
            .andExpect(jsonPath("\$.description").value("Console Nintendo Switch"))
    }

    @Test
    @Sql("/sql/product.sql")
    fun `should return 4xx when product exists`() {
        val cmd = RegisterProduct(
            name = "Nintendo Switch",
            description = "Console Nintendo Switch",
            categories = listOf("videogame")
        )

        mockMvc.perform(
            post("/product")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jacksonObjectMapper().writeValueAsString(cmd))
        )
            .andExpect(status().isConflict)
            .andExpect(jsonPath("\$.code").value("409"))
            .andExpect(jsonPath("\$.message").value("Product already exists"))
    }
}
