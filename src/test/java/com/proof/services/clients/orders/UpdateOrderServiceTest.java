package com.proof.services.clients.orders;

import com.proof.api.controllers.clients.orders.requests.UpdateOrderRequestDto;
import com.proof.exceptions.NotFoundException;
import com.proof.exceptions.ValidationException;
import com.proof.mappers.OrderMapper;
import com.proof.mothers.api.controllers.clients.orders.requests.UpdateOrderRequestDtoMother;
import com.proof.mothers.persistence.entities.OrderMother;
import com.proof.persistence.entities.Order;
import com.proof.persistence.repositories.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateOrderServiceTest {

    UpdateOrderService updateOrderService;
    OrderRepository orderRepository;
    OrderMapper orderMapper;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        orderMapper = mock(OrderMapper.class);
        updateOrderService = new UpdateOrderService(orderRepository, orderMapper);
    }

    @Test
    void updateOrderOk() {
        // given a set of parameters
        String clientCode = "0001";
        String orderNumber = "OR-0001";
        UpdateOrderRequestDto updateOrderRequestDto = UpdateOrderRequestDtoMother.validRequest();

        // and a recent order mock
        Order order = OrderMother.validOrder();
        order.setCreationDate(new Date());
        when(orderRepository.findByNumberAndClientCode(orderNumber, clientCode)).thenReturn(Optional.of(order));

        // and order mapper mock
        when(orderMapper.toEntity(any(UpdateOrderRequestDto.class))).thenReturn(OrderMother.validOrder());

        // when
        updateOrderService.updateOrder(clientCode, orderNumber, updateOrderRequestDto);

        // then
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void updateOrderWrongClientOrOrder() {
        // given a set of parameters
        String clientCode = "0001";
        String orderNumber = "OR-0001";
        UpdateOrderRequestDto updateOrderRequestDto = UpdateOrderRequestDtoMother.validRequest();

        // and order or client doesn't exist
        when(orderRepository.findByNumberAndClientCode(orderNumber, clientCode)).thenReturn(Optional.empty());

        // then
        assertThrows(NotFoundException.class, () ->
                updateOrderService.updateOrder(clientCode, orderNumber, updateOrderRequestDto));
    }

    @Test
    void updateOrderWrongOrderDate() {
        // given a set of parameters
        String clientCode = "0001";
        String orderNumber = "OR-0001";
        UpdateOrderRequestDto updateOrderRequestDto = UpdateOrderRequestDtoMother.validRequest();

        // and an old order mock
        Order order = OrderMother.validOrder();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -6);
        order.setCreationDate(calendar.getTime());
        when(orderRepository.findByNumberAndClientCode(orderNumber, clientCode)).thenReturn(Optional.of(order));

        // then
        assertThrows(ValidationException.class, () ->
                updateOrderService.updateOrder(clientCode, orderNumber, updateOrderRequestDto));
    }
}
