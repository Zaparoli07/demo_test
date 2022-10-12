package com.example.demo.app.controller

import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.Contains
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest
class ProductControllerTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun happyWay() {
        mockMvc.perform(get("/product").accept(MediaType.ALL))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("\$.name").value("Pizza"))
            .andExpect(jsonPath("\$.description").value("Teste"))
    }
}
