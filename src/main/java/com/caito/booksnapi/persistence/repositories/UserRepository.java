package com.caito.booksnapi.persistence.repositories;

import com.caito.booksnapi.persistence.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing UserApp entities.
 * Provides methods to perform CRUD operations and custom queries.
 * Extends JpaRepository for basic CRUD functionality.
 *
 * @author caito
 */
public interface UserRepository extends JpaRepository<UserApp, Long> {
    Optional<UserApp> findByEmail(String email);
    boolean existsByEmail(String email);
}
