package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
