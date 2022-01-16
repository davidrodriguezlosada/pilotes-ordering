package com.proof.services.orders;

import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;
import com.proof.api.dtos.OrderDto;
import com.proof.mappers.OrderMapper;
import com.proof.persistence.entities.Client;
import com.proof.persistence.entities.Order;
import com.proof.persistence.repositories.OrderRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

@Service
public class SearchOrdersService {

    @PersistenceContext
    private EntityManager entityManager;

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

    public List<OrderDto> search(String clientFilter) {
        if(StringUtils.isBlank(clientFilter)) {
            return search();
        } else {
            return searchWithFilter(clientFilter);
        }
    }

    private List<OrderDto> searchWithFilter(String clientFilter) {
        // 1.Create the JPA Visitor
        RSQLVisitor<CriteriaQuery<Client>, EntityManager> visitor = new JpaCriteriaQueryVisitor<>();
        // 2.Parse a RSQL into a Node
        Node rootNode = new RSQLParser().parse(clientFilter);
        // 3.Create CriteriaQuery
        CriteriaQuery<Client> criteriaQuery = rootNode.accept(visitor, entityManager);

        //TODO: Next step, make a single DB call using multiple entities in the criteria.
        List<?> clients = entityManager.createQuery(criteriaQuery).getResultList();

        Iterable<Order> orders = orderRepository.findAll();
        return StreamSupport.stream(orders.spliterator(), false)
                .filter(order -> clients.contains(order.getClient()))
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
}
