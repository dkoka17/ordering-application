package com.example.demo.service;


import org.springframework.stereotype.Service;

@Service
public class EmailService implements MessageSender{

    @Override
    public void send(String message) {
        System.out.println("Message sent:" + message);
    }
}
