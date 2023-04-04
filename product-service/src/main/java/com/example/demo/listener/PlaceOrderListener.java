package com.example.demo.listener;


import com.example.demo.config.MessageQueue;
import com.example.demo.model.dto.message.OrderMessage;
import com.example.demo.service.ProductsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaceOrderListener {

    @Autowired
    private ProductsService productsService;

    @RabbitListener(queues = MessageQueue.QUEUE)
    public void listener(OrderMessage message) {
        System.out.println("Message received: " + message.toString());
        productsService.updateProduct(message.getProductList());
    }
}
