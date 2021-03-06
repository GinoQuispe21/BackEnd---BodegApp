package com.bodegapp.demo.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SaveOrderResource {
    @NotBlank
    @NotNull
    private Date generated_date;

    @NotBlank
    @NotNull
    private double payment;
}
