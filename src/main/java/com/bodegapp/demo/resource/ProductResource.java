package com.bodegapp.demo.resource;

import lombok.Data;

@Data
public class ProductResource {
    private Long id;
    private String productName;
    private String providerName;
    private double salePrice;
    private double purchasePrice;
}
