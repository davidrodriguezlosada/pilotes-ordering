package com.proof.services.clients.orders;

import com.proof.api.controllers.clients.orders.requests.CreateOrderRequestDto;
import com.proof.api.dtos.OrderDto;
import com.proof.domain.Client;
import com.proof.domain.Order;
import com.proof.exceptions.NotFoundException;
import com.proof.mappers.OrderMapper;
import com.proof.repositories.ClientRepository;
import com.proof.repositories.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CreateOrderService {

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public CreateOrderService(
            ClientRepository clientRepository,
            OrderRepository orderRepository,
            OrderMapper orderMapper
    ) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public OrderDto createOrder(String clientCode, CreateOrderRequestDto createOrderRequestDto) {
        Client client = clientRepository
                .findByCode(clientCode)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        Order order = orderMapper.toEntity(createOrderRequestDto);
        order.setClient(client);
        order.setCreationDate(new Date());
        return orderMapper.toDto(orderRepository.save(order));
    }
}
