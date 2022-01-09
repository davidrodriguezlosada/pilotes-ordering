package com.proof.mothers.api.controllers.clients.orders.requests;

import com.proof.api.controllers.clients.orders.requests.UpdateOrderRequestDto;
import com.proof.mothers.api.dtos.AddressDtoMother;

public class UpdateOrderRequestDtoMother {

    public static UpdateOrderRequestDto validRequest() {
        UpdateOrderRequestDto updateOrderRequestDto = new UpdateOrderRequestDto();
        updateOrderRequestDto.setDeliveryAddress(AddressDtoMother.validAddress());
        updateOrderRequestDto.setPilotes(5);
        updateOrderRequestDto.setOrderTotal(10.0);
        return updateOrderRequestDto;
    }

    public static UpdateOrderRequestDto invalidRequest() {
        return new UpdateOrderRequestDto();
    }
}
