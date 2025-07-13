package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
