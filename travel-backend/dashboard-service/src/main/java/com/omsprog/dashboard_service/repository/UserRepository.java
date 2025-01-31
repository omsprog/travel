package com.omsprog.dashboard_service.repository;

import com.omsprog.dashboard_service.entity.jpa.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUserEntity, String> {
    Optional<AppUserEntity> findByEmail(String email);
    Optional<AppUserEntity> findByDni(String dni);
}