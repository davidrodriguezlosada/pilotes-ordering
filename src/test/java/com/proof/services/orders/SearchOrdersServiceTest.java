package com.proof.services.orders;

import com.proof.api.dtos.OrderDto;
import com.proof.mothers.persistence.entities.OrderMother;
import com.proof.persistence.repositories.OrderRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class SearchOrdersServiceTest {

    OrderRepository orderRepository;
    SearchOrdersService searchOrdersService;

    @Autowired
    public SearchOrdersServiceTest(OrderRepository orderRepository,
                                   SearchOrdersService searchOrdersService) {
        this.orderRepository = orderRepository;
        this.searchOrdersService = searchOrdersService;
    }

    @Test
    void searchOk() {
        // given an order
        orderRepository.save(OrderMother.validOrder());

        // when
        List<OrderDto> orderDtos = searchOrdersService.search();

        assertAll(
                () -> assertNotNull(orderDtos),
                () -> assertEquals(1, orderDtos.size())
        );
    }

    @Test
    void searchWithExpressionOk() {
        // given an order
        orderRepository.save(OrderMother.validOrder());

        // and an expression
        String clientFilter = "firstName==unknown";

        // when
        List<OrderDto> orderDtos = searchOrdersService.search(clientFilter);

        assertAll(
                () -> assertNotNull(orderDtos),
                () -> assertEquals(0, orderDtos.size())
        );
    }
}
