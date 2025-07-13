package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
