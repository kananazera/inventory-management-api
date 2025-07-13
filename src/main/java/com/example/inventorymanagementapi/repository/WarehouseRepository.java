package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
