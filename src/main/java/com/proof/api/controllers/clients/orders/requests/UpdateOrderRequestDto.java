package com.proof.api.controllers.clients.orders.requests;

import com.proof.api.dtos.AddressDto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateOrderRequestDto {

    @NotNull
    private AddressDto deliveryAddress;

    @NotNull
    private Integer pilotes;

    @NotNull
    private Double orderTotal;
}