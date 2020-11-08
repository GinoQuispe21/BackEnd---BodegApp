package com.bodegapp.demo.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveProductResource {
    @NotNull
    @NotBlank
    @Column(unique = true)
    @Size(max = 50)
    private String productName;

    @NotNull
    @NotBlank
    private String providerName;

    @NotNull
    @NotBlank
    private double salePrice;

    @NotNull
    @NotBlank
    private double purchasePrice;
}
