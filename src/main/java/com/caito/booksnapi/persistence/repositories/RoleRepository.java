package com.caito.booksnapi.persistence.repositories;

import com.caito.booksnapi.persistence.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Role entities.
 * Provides methods to perform CRUD operations and custom queries.
 * Extends JpaRepository for basic CRUD functionality.
 *
 * @author caito
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
