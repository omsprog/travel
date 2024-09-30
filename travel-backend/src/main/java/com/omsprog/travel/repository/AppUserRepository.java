package com.omsprog.travel.repository;

import com.omsprog.travel.entity.mongo.AppUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUserDocument, String> {
    Optional<AppUserDocument> findByUsername(String username);
}