package com.bodegapp.demo.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SaveCustomerAccountResource {
    @NotBlank
    @NotNull
    private double currentBalance;

    @NotBlank
    @NotNull
    private double credit;

    @NotBlank
    @NotNull
    private double interestRate;

    @NotBlank
    @NotNull
    private int interestRateType;

    @NotBlank
    @NotNull
    private int interestRatePeriod;

    @NotBlank
    @NotNull
    private int compounding;

    @NotBlank
    @NotNull
    private int typeYear;
}
