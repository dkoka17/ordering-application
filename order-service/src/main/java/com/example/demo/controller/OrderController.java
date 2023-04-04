package com.example.demo.controller;

import com.example.demo.model.dto.request.ProductList;
import com.example.demo.model.dto.response.PlaceOrderResponse;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping("/place-order")
    public ResponseEntity<?> placeOrder(@RequestBody ProductList request) {
        orderService.placeOrder(request);
        return ResponseEntity.ok(new PlaceOrderResponse("Order placed successfully"));
    }
}