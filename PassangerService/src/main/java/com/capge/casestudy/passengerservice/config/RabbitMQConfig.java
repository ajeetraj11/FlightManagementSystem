package com.capge.casestudy.passengerservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
	   return new Jackson2JsonMessageConverter(); // No type mapping
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
	   RabbitTemplate template = new RabbitTemplate(connectionFactory);
	   template.setMessageConverter(jsonMessageConverter());
	   return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
	   SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	   factory.setConnectionFactory(connectionFactory);
	   factory.setMessageConverter(jsonMessageConverter());
	   return factory;
    }
}
