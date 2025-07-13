package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
