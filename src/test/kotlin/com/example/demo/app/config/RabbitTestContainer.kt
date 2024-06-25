package com.example.demo.app.config

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
interface RabbitTestContainer {

    companion object {
        private val rabbit = RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"))

        @BeforeAll
        @JvmStatic
        fun startDBContainer() {
            rabbit.start()
        }

        @AfterAll
        @JvmStatic
        fun stopDBContainer() {
            rabbit.stop()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerRabbitContainer(registry: DynamicPropertyRegistry) {
            registry.add("spring.rabbitmq.host", rabbit::getHost)
            registry.add("spring.rabbitmq.port", rabbit::getAmqpPort)
            registry.add("spring.rabbitmq.username", rabbit::getAdminUsername)
            registry.add("spring.rabbitmq.password", rabbit::getAdminPassword)
        }
    }
}
