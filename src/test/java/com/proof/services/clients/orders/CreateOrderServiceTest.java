package com.proof.services.clients.orders;

import com.proof.api.controllers.clients.orders.requests.CreateOrderRequestDto;
import com.proof.exceptions.NotFoundException;
import com.proof.mappers.OrderMapper;
import com.proof.mothers.api.controllers.clients.orders.requests.CreateOrderRequestDtoMother;
import com.proof.mothers.persistence.entities.ClientMother;
import com.proof.mothers.persistence.entities.OrderMother;
import com.proof.persistence.repositories.ClientRepository;
import com.proof.persistence.repositories.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class CreateOrderServiceTest {

    CreateOrderService createOrderService;
    ClientRepository clientRepository;
    OrderRepository orderRepository;
    OrderMapper orderMapper;

    @BeforeEach
    void setup() {
        clientRepository = mock(ClientRepository.class);
        orderRepository = mock(OrderRepository.class);
        orderMapper = mock(OrderMapper.class);
        createOrderService = new CreateOrderService(
                clientRepository,
                orderRepository,
                orderMapper
        );
    }

    @Test
    void createOrderOk() {
        // given a client and a request
        String clientCode = "00001";
        CreateOrderRequestDto createOrderRequestDto = CreateOrderRequestDtoMother.validRequest();

        when(clientRepository.findByCode(clientCode)).thenReturn(Optional.of(ClientMother.validClient()));
        when(orderMapper.toEntity(any(CreateOrderRequestDto.class))).thenReturn(OrderMother.validOrder());

        // when
        createOrderService.createOrder(clientCode, createOrderRequestDto);

        // then
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void createOrderWithNotExistingCustomer() {
        // given
        String clientCode = "00001";
        CreateOrderRequestDto createOrderRequestDto = CreateOrderRequestDtoMother.validRequest();

        when(clientRepository.findByCode(clientCode)).thenReturn(Optional.empty());

        // then
        assertThrows(NotFoundException.class, () -> createOrderService.createOrder(clientCode, createOrderRequestDto));
    }
}
