package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Inventory;
import az.inventory.inventorymanagementapi.entity.Product;
import az.inventory.inventorymanagementapi.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductAndWarehouse(Product product, Warehouse warehouse);
}
