package com.omsprog.travel.repository;

import com.omsprog.travel.entity.jpa.TourEntity;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<TourEntity, Long> {

}