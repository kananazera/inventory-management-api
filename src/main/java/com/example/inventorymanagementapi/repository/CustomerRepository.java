package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
