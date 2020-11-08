package com.bodegapp.demo.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SaveDeliveryResource {
    @NotNull
    @NotBlank
    private String typeDelivery;

    @NotNull
    @NotBlank
    private double deliveryPrice;
}
