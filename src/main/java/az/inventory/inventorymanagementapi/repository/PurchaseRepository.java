package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
