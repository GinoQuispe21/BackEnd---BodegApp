package com.bodegapp.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name ="products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    @Size(max = 50)
    private String productName;

    @NotNull
    @NotBlank
    private String providerName;

    //Precio de venta - usar este para calcular el precio de la orden
    @NotNull
    @NotBlank
    private double salePrice;

    //Precio de compra
    @NotNull
    @NotBlank
    private double purchasePrice;

    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails;
}
