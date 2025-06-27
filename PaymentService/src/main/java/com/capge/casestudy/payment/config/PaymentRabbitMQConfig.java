package com.capge.casestudy.payment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRabbitMQConfig {

    // Payment Processing Queue Configuration
    public static final String PAYMENT_PROCESS_QUEUE = "queue.payment.process";
    public static final String PAYMENT_EXCHANGE = "payment.exchange";
    public static final String PAYMENT_ROUTING_KEY = "payment.routing.key";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Define Payment Queue
    @Bean
    public Queue paymentProcessQueue() {
        return new Queue(PAYMENT_PROCESS_QUEUE, true);
    }

    // Define Payment Exchange
    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(PAYMENT_EXCHANGE);
    }

    // Bind Queue to Exchange with Routing Key
    @Bean
    public Binding paymentBinding() {
        return BindingBuilder.bind(paymentProcessQueue()).to(paymentExchange()).with(PAYMENT_ROUTING_KEY);
    }

    // Configure RabbitTemplate for message conversion
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}