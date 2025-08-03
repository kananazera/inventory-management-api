package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
