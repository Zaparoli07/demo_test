package com.example.demo.config.rabbit

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig(
    @Value("\${rabbitmq.exchange}")
    val topicExchangeName: String,
    @Value("\${rabbitmq.queue}")
    val queueName: String
) {

    @Bean
    fun queueDemo(): Queue {
        return Queue(queueName, true, false, false)
    }

    @Bean
    fun exchangeDemo(): TopicExchange {
        return TopicExchange(topicExchangeName, true, false)
    }

    @Bean
    fun bindingDemo(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with("demo")
    }

    @Bean
    fun messageConverter(objectMapper: ObjectMapper): MessageConverter {
        return Jackson2JsonMessageConverter(objectMapper)
    }
}
