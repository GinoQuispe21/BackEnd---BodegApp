package com.bodegapp.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Column(unique = true)
    private int dni;

    @NotBlank
    @NotNull
    @Column(unique = true)
    @Size(max = 50)
    private String customerName;

    @NotBlank
    @NotNull
    @Size(max = 50) 
    private String customerLastname;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String address;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String district;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String country;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String email;

    //Estado del customer: 0: CLiente Activo 1: Cliente Eliminado

    private int state;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "customer")
    private CustomerAccount customerAccount;
}
