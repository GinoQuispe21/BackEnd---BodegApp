package com.bodegapp.demo.resource;

import lombok.Data;

import java.util.Date;

@Data
public class MovementResource {
    private Long id;
    private Date generated_date;
    private double amount;
}
