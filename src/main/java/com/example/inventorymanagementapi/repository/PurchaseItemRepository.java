package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
