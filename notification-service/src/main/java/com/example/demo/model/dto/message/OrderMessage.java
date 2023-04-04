package com.example.demo.model.dto.message;

import com.example.demo.model.dto.request.ProductList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderMessage {
    private Date messageDate;
    private String orderId;
    private ProductList productList;
}
