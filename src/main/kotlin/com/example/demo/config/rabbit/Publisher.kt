package com.example.demo.config.rabbit

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Publisher(
    @Value("\${rabbitmq.exchange}")
    val exchange: String,
    @Value("\${rabbitmq.queue}")
    val queue: String,
    val rabbitTemplate: RabbitTemplate
) {

    fun publish(message: Any) {
        rabbitTemplate.convertAndSend(exchange, queue, message)
    }
}
