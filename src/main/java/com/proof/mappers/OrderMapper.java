package com.proof.mappers;

import com.proof.api.controllers.clients.orders.requests.CreateOrderRequestDto;
import com.proof.api.controllers.clients.orders.requests.UpdateOrderRequestDto;
import com.proof.api.dtos.OrderDto;
import com.proof.domain.Order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    Order toEntity(OrderDto orderDto);

    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    Order toEntity(CreateOrderRequestDto createOrderRequestDto);

    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    Order toEntity(UpdateOrderRequestDto updateOrderRequestDto);

    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    OrderDto toDto(Order order);
}
