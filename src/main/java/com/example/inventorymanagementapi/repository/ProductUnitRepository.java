package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.ProductUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface ProductUnitRepository extends JpaRepository<ProductUnit, Long>, JpaSpecificationExecutor<ProductUnit> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(@Param("name") String name, @Param("id") Long id);
}
