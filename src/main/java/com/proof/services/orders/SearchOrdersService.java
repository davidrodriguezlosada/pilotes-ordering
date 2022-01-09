package com.proof.services.orders;

import com.proof.api.dtos.OrderDto;
import com.proof.api.filtering.expressions.Expression;
import com.proof.mappers.OrderMapper;
import com.proof.persistence.entities.Client;
import com.proof.persistence.entities.Order;
import com.proof.persistence.filtering.ExpressionToCriteria;
import com.proof.persistence.repositories.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

@Service
public class SearchOrdersService {

    @PersistenceContext
    private EntityManager entityManager;

    OrderRepository orderRepository;
    OrderMapper orderMapper;
    ExpressionToCriteria<Client> expressionToCriteria;

    public SearchOrdersService(OrderRepository orderRepository,
                               OrderMapper orderMapper,
                               ExpressionToCriteria<Client> expressionToCriteria) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.expressionToCriteria = expressionToCriteria;
    }

    public List<OrderDto> search() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderDto> search(Expression<Object, Object> expression) {

        //TODO: Next step, make a single DB call using multiple entities in the criteria.
        CriteriaQuery<?> criteriaQuery = expressionToCriteria.createCriteria(expression, Client.class);
        List<?> clients = entityManager.createQuery(criteriaQuery).getResultList();

        Iterable<Order> orders = orderRepository.findAll();
        return StreamSupport.stream(orders.spliterator(), false)
                .filter(order -> clients.contains(order.getClient()))
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
}
