package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
