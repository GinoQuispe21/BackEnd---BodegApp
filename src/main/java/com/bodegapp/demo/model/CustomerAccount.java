package com.bodegapp.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers_accounts")
@Data
public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Valor Futuro de la cuenta
    @NotBlank
    @NotNull
    private double currentBalance;

    //Monto del credito
    @NotBlank
    @NotNull
    private double credit;

    //Tasa en % del interes
    @NotBlank
    @NotNull
    private double interestRate;

    //1: Simple, 2: Nominal, 3: Efectivo
    @NotBlank
    @NotNull
    private int interestRateType;

    //Periodo de la tasa de interes
    //1:Mensual, 2:Bimestral, 3:Trimestral, 4:Cuatrimestral, 5:Semestral, 6:Anual
    @NotBlank
    @NotNull
    private int interestRatePeriod;

    //Capitalizacion
    //0: Null, 1:Diaria, 2:Semanal
    @NotBlank
    @NotNull
    private int compounding;

    //1: Año Ordinario, 2: Año Exacto
    @NotBlank
    @NotNull
    private int typeYear;

    private Date firstDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "customerAccount")
    List<Movement> Movements;
}
