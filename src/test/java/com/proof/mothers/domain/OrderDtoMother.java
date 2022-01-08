package com.proof.mothers.domain;

import com.proof.api.dtos.OrderDto;

public class OrderDtoMother {

    public static OrderDto validOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setNumber("00001");
        orderDto.setClient(ClientDtoMother.validClient());
        orderDto.setDeliveryAddress(AddressDtoMother.validAddress());
        orderDto.setPilotes(5);
        orderDto.setOrderTotal(25.0);
        return orderDto;
    }
}
