package com.example.demo.app.controller

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class IntegrationTest {

    companion object {
        // Imagem do container do Docker para o banco de dados Postgres
        private val db = PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))

        @BeforeAll // Oque será executado antes dos testes
        @JvmStatic
        fun startDBContainer() {
            db.start()
        }

        @AfterAll // Oque será executado após os testes
        @JvmStatic
        fun stopDBContainer() {
            db.stop()
        }

        @DynamicPropertySource // Propriedades de ambiente do banco de dados
        @JvmStatic
        fun registerDBContainer(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", db::getJdbcUrl)
            registry.add("spring.datasource.username", db::getUsername)
            registry.add("spring.datasource.password", db::getPassword)
        }
    }
}
