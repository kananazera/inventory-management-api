package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.ProductUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductUnitRepository extends JpaRepository<ProductUnit, Long>, JpaSpecificationExecutor<ProductUnit> {
}
