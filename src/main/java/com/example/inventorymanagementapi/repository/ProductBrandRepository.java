package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {
}
