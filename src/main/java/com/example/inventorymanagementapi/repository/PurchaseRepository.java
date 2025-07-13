package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
