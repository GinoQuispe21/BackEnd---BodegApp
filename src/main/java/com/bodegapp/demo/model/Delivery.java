package com.bodegapp.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name ="deliveries")
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String typeDelivery;

    @NotNull
    @NotBlank
    private double deliveryPrice;

    @OneToOne(mappedBy = "delivery")
    private Order order;
}
