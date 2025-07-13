package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
