package com.proof.api.dtos;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrderDto {
    @NotBlank
    @Size(max = 50)
    private String number;

    @NotNull
    private AddressDto deliveryAddress;

    @NotNull
    private ClientDto client;

    @NotNull
    private Integer pilotes;

    @NotNull
    private Double orderTotal;

    @NotNull
    private Date creationDate;
}
