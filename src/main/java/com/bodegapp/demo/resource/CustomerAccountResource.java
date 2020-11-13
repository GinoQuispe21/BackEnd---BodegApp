package com.bodegapp.demo.resource;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerAccountResource {
    private Long id;
    private double currentBalance;
    private double credit;
    private double interestRate;
    private int  interestRateType;
    private int interestRatePeriod;
    private int compounding;
    private int typeYear;
    private Date firstDate;
}
