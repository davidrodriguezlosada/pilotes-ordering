package com.proof.persistence.repositories;

import com.proof.persistence.entities.Order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findByNumberAndClientCode(String number, String clientCode);
}
