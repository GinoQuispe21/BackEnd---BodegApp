package com.bodegapp.demo.resource;

import lombok.Data;

@Data
public class DeliveryResource {
    private Long id;
    private String typeDelivery;
    private double deliveryPrice;
}
