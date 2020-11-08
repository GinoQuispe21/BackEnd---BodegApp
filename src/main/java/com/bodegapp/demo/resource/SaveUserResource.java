package com.bodegapp.demo.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter

public class SaveUserResource {
    @NotBlank
    @NotNull
    @Column(unique = true)
    @Size(max = 50)
    private int dni;

    @NotBlank
    @NotNull
    @Column(unique = true)
    @Size(max = 50)
    private String username;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String lastname;

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

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String phoneNumber;

    @NotBlank
    @NotNull
    private Date birthdate;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String password;
}
