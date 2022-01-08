package com.proof.services.orders;

import com.proof.api.dtos.OrderDto;
import com.proof.mappers.OrderMapper;
import com.proof.repositories.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SearchOrdersService {

    OrderRepository orderRepository;
    OrderMapper orderMapper;

    public SearchOrdersService(OrderRepository orderRepository,
                               OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDto> search() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
}
