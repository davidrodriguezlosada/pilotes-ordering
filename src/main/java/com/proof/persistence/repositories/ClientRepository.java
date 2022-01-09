package com.proof.persistence.repositories;

import com.proof.persistence.entities.Client;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Optional<Client> findByCode(String clientCode);
}
