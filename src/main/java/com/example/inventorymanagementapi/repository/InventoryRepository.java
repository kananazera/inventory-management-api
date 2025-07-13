package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
