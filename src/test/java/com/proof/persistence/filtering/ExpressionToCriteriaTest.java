package com.proof.persistence.filtering;

import com.proof.api.filtering.expressions.CompareExpression;
import com.proof.api.filtering.expressions.Expression;
import com.proof.api.filtering.expressions.LogicalExpression;
import com.proof.api.filtering.operators.CompareOperator;
import com.proof.api.filtering.operators.LogicalOperator;
import com.proof.persistence.entities.Client;
import com.proof.persistence.repositories.ClientRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ExpressionToCriteriaTest {

    @PersistenceContext
    private EntityManager entityManager;

    ClientRepository clientRepository;
    ExpressionToCriteria<Client> expressionToCriteria;

    @Autowired
    ExpressionToCriteriaTest(ClientRepository clientRepository,
                             ExpressionToCriteria<Client> expressionToCriteria) {
        this.clientRepository = clientRepository;
        this.expressionToCriteria = expressionToCriteria;
    }

    @Test
    void testEqualsExpression() {
        // given two clients
        Client clientJohn = getClientJohn();
        Client clientJoan = getClientJoan();
        clientRepository.save(clientJohn);
        clientRepository.save(clientJoan);

        // and an expression
        Expression<String, String> expression = new CompareExpression<>("firstName", "John", CompareOperator.EQUAL);

        // when
        CriteriaQuery<?> criteriaQuery = expressionToCriteria.createCriteria(expression, Client.class);
        List<?> clients = entityManager.createQuery(criteriaQuery).getResultList();

        // then
        assertAll(
                () -> assertNotNull(clients),
                () -> assertEquals(1, clients.size()),
                () -> assertEquals(clientJohn, clients.get(0))
        );
    }

    @Test
    void testLikeExpression() {
        // given two clients
        Client clientJohn = getClientJohn();
        Client clientJoan = getClientJoan();
        clientRepository.save(clientJohn);
        clientRepository.save(clientJoan);

        // and an expression
        Expression<String, String> expression = new CompareExpression<>("firstName", "Jo%", CompareOperator.LIKE);

        // when
        CriteriaQuery<?> criteriaQuery = expressionToCriteria.createCriteria(expression, Client.class);
        List<?> clients = entityManager.createQuery(criteriaQuery).getResultList();

        // then
        assertAll(
                () -> assertNotNull(clients),
                () -> assertEquals(2, clients.size()),
                () -> assertTrue(clients.contains(clientJohn)),
                () -> assertTrue(clients.contains(clientJoan))
        );
    }

    @Test
    void testLogicalExpression() {
        // given two clients
        Client clientJohn = getClientJohn();
        Client clientJoan = getClientJoan();
        clientRepository.save(clientJohn);
        clientRepository.save(clientJoan);

        // and an expression
        Expression<String, String> expressionA = new CompareExpression<>("firstName", "Jo%", CompareOperator.LIKE);
        Expression<String, String> expressionB = new CompareExpression<>("lastName", "Doyle", CompareOperator.EQUAL);
        Expression<Object, Object> expression = new LogicalExpression<>(expressionA, expressionB, LogicalOperator.AND);

        // when
        CriteriaQuery<?> criteriaQuery = expressionToCriteria.createCriteria(expression, Client.class);
        List<?> clients = entityManager.createQuery(criteriaQuery).getResultList();

        // then
        assertAll(
                () -> assertNotNull(clients),
                () -> assertEquals(1, clients.size()),
                () -> assertEquals(clientJoan, clients.get(0))
        );
    }

    private Client getClientJohn() {
        Client clientJohn = new Client();
        clientJohn.setCode("00001");
        clientJohn.setFirstName("John");
        clientJohn.setLastName("Doe");
        clientJohn.setTelephone("600100200");
        return clientJohn;
    }

    private Client getClientJoan() {
        Client clientJoan = new Client();
        clientJoan.setCode("00002");
        clientJoan.setFirstName("Joan");
        clientJoan.setLastName("Doyle");
        clientJoan.setTelephone("600800500");
        return clientJoan;
    }
}