package com.example.demo.service;


import com.example.demo.model.entity.Delivery;
import com.example.demo.model.enums.DeliveryState;
import com.example.demo.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public List<Delivery> getDeliveries() {
        return deliveryRepository.findAll();
    }

    public void updateDeliveryInformation(String orderId) {
        Delivery delivery = new Delivery();
        delivery.setOrder_id(orderId);
        delivery.setName(DeliveryState.PENDING);
        deliveryRepository.save(delivery);
    }
}
