package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String name);

    boolean existsByUsernameIgnoreCaseAndIdNot(@Param("username") String name, @Param("id") Long id);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(@Param("email") String name, @Param("id") Long id);
}