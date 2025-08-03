package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
