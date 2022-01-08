package com.proof.repositories;

import com.proof.domain.Order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findByNumberAndClientCode(String number, String clientCode);
}
