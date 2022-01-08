package com.proof.services.clients.orders;

import com.proof.api.controllers.clients.orders.requests.UpdateOrderRequestDto;
import com.proof.api.dtos.OrderDto;
import com.proof.domain.Order;
import com.proof.exceptions.NotFoundException;
import com.proof.exceptions.ValidationException;
import com.proof.mappers.OrderMapper;
import com.proof.repositories.OrderRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class UpdateOrderService {

    public static final int VALID_UPDATE_PERIOD_IN_MINUTES = -5;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public UpdateOrderService(OrderRepository orderRepository,
                              OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public OrderDto updateOrder(String clientCode, String orderNumber, UpdateOrderRequestDto updateOrderRequestDto) {
        Order order = orderRepository
                .findByNumberAndClientCode(orderNumber, clientCode)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (isValidModifyDate(order.getCreationDate())) {
            throw new ValidationException("Order modification time has passed");
        }

        Order newOrder = orderMapper.toEntity(updateOrderRequestDto);
        BeanUtils.copyProperties(newOrder, order, "id", "number");

        return orderMapper.toDto(orderRepository.save(order));
    }

    private boolean isValidModifyDate(Date orderDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, VALID_UPDATE_PERIOD_IN_MINUTES);
        return orderDate.before(calendar.getTime());
    }
}
