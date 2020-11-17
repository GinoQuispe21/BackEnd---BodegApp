package com.bodegapp.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private Date generated_date;

    @NotBlank
    @NotNull
    private double payment;

    //Relacion de muchos a muchos con productos
    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;

    //Relacion de uno a muchos de customer y order
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;


    //Relacion de uno a muchos entre order con delivery
    //@ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@JoinColumn(name = "delivery_id", nullable = false)
    //@JsonIgnore
    //private Delivery delivery;

    //Relacion de uno a uno entre order y movement
    @OneToOne(mappedBy = "order")
    private Movement movement;
}
