package com.example.mappingservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String INTERNAL_EXCHANGE = "internal_exchange";

    public static final String MAPPING_QUEUE = "monitoring.mapping";
    public static final String MAPPING_ROUTING_KEY = "mapping_routingkey";

    public static final String EXECUTE_MIGRATION_ROUTING_KEY = "migration_routingkey";

    @Bean
    public Queue queue() {
        return new Queue(MAPPING_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(INTERNAL_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MAPPING_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
