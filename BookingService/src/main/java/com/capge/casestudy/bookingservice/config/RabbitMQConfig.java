package com.capge.casestudy.bookingservice.config;

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
public class RabbitMQConfig {


    // Passenger Queue Configuration
    public static final String BOOKING_QUEUE = "queue.passenger.response";
    public static final String BOOKING_EXCHANGE ="queue.passenger.exchange";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Todo: Creating Queue, Exchange and The Binding to make Communication with the Passenger Service
    @Bean
    public Queue passengerQueue(){
        return new Queue(BOOKING_QUEUE);
    }

    @Bean
    public TopicExchange passengerExchange(){
        return new TopicExchange(BOOKING_EXCHANGE);
    }

    @Bean
    public Binding passengerBinding(){
        return BindingBuilder.bind(passengerQueue()).to(passengerExchange()).with("queue.response.key");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
