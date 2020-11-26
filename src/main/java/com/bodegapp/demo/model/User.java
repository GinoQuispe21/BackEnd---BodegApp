package com.bodegapp.demo.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Column(unique = true)
    @Size(max = 8)
    private int dni;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String username;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String lastname;

    @NotBlank
    @NotNull
    @Size(max = 100)
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
    @Column(unique = true)
    @Size(max = 50)
    private String email;

    @NotBlank
    @NotNull
    @Column(unique = true)
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
