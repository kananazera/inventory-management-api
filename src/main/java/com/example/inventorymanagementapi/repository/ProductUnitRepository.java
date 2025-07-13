package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.ProductUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUnitRepository extends JpaRepository<ProductUnit, Long> {
}
