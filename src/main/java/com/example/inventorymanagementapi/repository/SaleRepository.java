package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
