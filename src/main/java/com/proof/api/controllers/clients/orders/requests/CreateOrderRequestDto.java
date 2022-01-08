package com.proof.api.controllers.clients.orders.requests;

import com.proof.api.dtos.AddressDto;
import com.proof.validations.ValidPilotesNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateOrderRequestDto {
    @NotBlank
    @Size(max = 50)
    private String number;

    @NotNull
    private AddressDto deliveryAddress;

    @NotNull
    @ValidPilotesNumber
    private Integer pilotes;

    @NotNull
    private Double orderTotal;
}