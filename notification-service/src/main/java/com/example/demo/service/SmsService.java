package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("Message sent:" + message);
    }
}
