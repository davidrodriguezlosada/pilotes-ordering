package com.proof.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "ADDRESSES")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String street;

    @NotBlank
    @Size(max = 255)
    private String postcode;

    @NotBlank
    @Size(max = 255)
    private String city;

    @NotBlank
    @Size(max = 255)
    private String country;
}
