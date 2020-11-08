package com.bodegapp.demo.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter

public class SaveCustomerResource {
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
}
