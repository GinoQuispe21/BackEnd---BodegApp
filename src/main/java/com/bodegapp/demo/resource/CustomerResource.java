package com.bodegapp.demo.resource;

import lombok.Data;

@Data
public class CustomerResource {
    private Long id;
    private int dni;
    private String customerName;
    private String customerLastname;
    private String address;
    private String district;
    private String country;
    private String email;
    private int state;
}
