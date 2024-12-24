package com.omsprog.travel.repository;

import com.omsprog.travel.entity.jpa.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByEmail(String email);
    Optional<CustomerEntity> findByDni(String dni);
}