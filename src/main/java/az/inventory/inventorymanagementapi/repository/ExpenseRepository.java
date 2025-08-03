package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
