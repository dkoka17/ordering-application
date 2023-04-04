package com.example.demo.model.entity;
import com.example.demo.model.enums.DeliveryState;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String order_id;

    @Column
    private DeliveryState name;

}