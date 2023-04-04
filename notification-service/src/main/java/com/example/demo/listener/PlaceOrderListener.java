package com.example.demo.listener;


import com.example.demo.config.MessageQueue;
import com.example.demo.model.dto.message.OrderMessage;
import com.example.demo.service.EmailService;
import com.example.demo.service.SmsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaceOrderListener {

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = MessageQueue.NOTIFICATION_QUEUE)
    public void listener(OrderMessage message) {
        System.out.println("Got message:" + message.toString());
        smsService.send(message.toString());
        emailService.send(message.toString());
    }
}
