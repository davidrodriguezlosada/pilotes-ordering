package com.proof.mothers.api.controllers.clients.orders.requests;

import com.proof.api.controllers.clients.orders.requests.CreateOrderRequestDto;
import com.proof.mothers.domain.AddressDtoMother;

public class CreateOrderRequestDtoMother {

    public static CreateOrderRequestDto validRequest() {
        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto();
        createOrderRequestDto.setNumber("OR-0001");
        createOrderRequestDto.setPilotes(5);
        createOrderRequestDto.setOrderTotal(15.0);
        createOrderRequestDto.setDeliveryAddress(AddressDtoMother.validAddress());
        return createOrderRequestDto;
    }

    public static CreateOrderRequestDto invalidRequest() {
        return new CreateOrderRequestDto();
    }
}
