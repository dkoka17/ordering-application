package com.example.demo.service;

import com.example.demo.config.MessageQueue;
import com.example.demo.model.dto.message.OrderMessage;
import com.example.demo.model.dto.request.ProductList;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
public class OrderService {

    @Autowired
    private RabbitTemplate template;

    public void placeOrder(ProductList productList) {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOrderId(UUID.randomUUID().toString());
        orderMessage.setMessageDate(new Date());
        orderMessage.setProductList(productList);

        template.convertAndSend(MessageQueue.EXCHANGE, MessageQueue.ROUTING_NOTIFICATION_KEY, orderMessage);
        template.convertAndSend(MessageQueue.EXCHANGE, MessageQueue.ROUTING_PACKAGING_KEY, orderMessage);
        template.convertAndSend(MessageQueue.EXCHANGE, MessageQueue.ROUTING_PRODUCTS_KEY, orderMessage);

        System.out.println("message sent");
    }
}
