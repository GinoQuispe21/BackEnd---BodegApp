package com.bodegapp.demo.model;

import lombok.Data;

@Data
public class CartLine {
    private Long id;
    private String productName;
    private String providerName;
    private double salePrice;
    private int quantity;
}
