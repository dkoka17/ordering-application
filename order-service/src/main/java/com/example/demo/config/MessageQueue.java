package com.example.demo.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessageQueue {

    public static final String NOTIFICATION_QUEUE = "notification_queue";
    public static final String PACKAGING_QUEUE = "packaging_queue";
    public static final String PRODUCT_QUEUE = "product_queue";
    public static final String EXCHANGE = "message_exchange";
    public static final String ROUTING_NOTIFICATION_KEY = "message_notification_routingKey";
    public static final String ROUTING_PACKAGING_KEY = "message_packaging_routingKey";
    public static final String ROUTING_PRODUCTS_KEY = "message_product_routingKey";

    @Bean
    public Queue notification() {
        return new Queue(NOTIFICATION_QUEUE);
    }

    @Bean
    public Queue packaging() {
        return new Queue(PACKAGING_QUEUE);
    }

    @Bean
    public Queue product() {
        return new Queue(PRODUCT_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(TopicExchange exchange) {
        return BindingBuilder
                .bind(notification())
                .to(exchange)
                .with(ROUTING_NOTIFICATION_KEY);
    }


    @Bean
    public Binding binding1(TopicExchange exchange) {
        return BindingBuilder
                .bind(packaging())
                .to(exchange)
                .with(ROUTING_PACKAGING_KEY);
    }

    @Bean
    public Binding binding2(TopicExchange exchange) {
        return BindingBuilder
                .bind(product())
                .to(exchange)
                .with(ROUTING_PRODUCTS_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }

}
