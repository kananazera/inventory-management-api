package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
