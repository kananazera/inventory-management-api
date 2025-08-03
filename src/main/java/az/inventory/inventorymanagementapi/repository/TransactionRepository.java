package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
