package com.omsprog.travel.repository;

import com.omsprog.travel.entity.jpa.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}