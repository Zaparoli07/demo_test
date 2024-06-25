package com.example.demo.app.controller

import com.example.demo.app.config.PostgresTestContainer
import com.example.demo.app.config.RabbitTestContainer
import com.example.demo.product.usecase.RegisterProductUseCase.RegisterProduct
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation::class)
internal class ProductControllerTest(
    @Value("\${rabbitmq.queue}")
    val queueName: String
) : PostgresTestContainer, RabbitTestContainer {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var rabbitAdmin: RabbitAdmin

    @AfterEach
    fun afterEach() {
        rabbitAdmin.purgeQueue(queueName)
    }

    @Test
    @DisplayName("Caminho Feliz")
    @Order(1)
    fun `should return 2xx when save product`() {
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

        assertEquals(1, rabbitAdmin.getQueueInfo(queueName).messageCount)
    }

    @Test
    @DisplayName("Caminho Triste")
    @Order(2)
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

        assertEquals(0, rabbitAdmin.getQueueInfo(queueName).messageCount)
    }
}
