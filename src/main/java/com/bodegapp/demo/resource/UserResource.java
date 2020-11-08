package com.bodegapp.demo.resource;

import lombok.Data;

import java.util.Date;

@Data
public class UserResource {
    private Long id;
    private int dni;
    private String username;
    private String lastname;
    private String address;
    private String district;
    private String country;
    private String email;
    private String phoneNumber;
    private Date birthdate;
    private String password;
}
