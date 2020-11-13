package com.bodegapp.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private Date generated_date;

    @NotBlank
    @NotNull
    private double payment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_account_id", nullable = false)
    @JsonIgnore
    private CustomerAccount customerAccount;

    //Relacion de uno a uno entre order y movement
    @OneToOne(mappedBy = "payment")
    private Movement movement;
}
