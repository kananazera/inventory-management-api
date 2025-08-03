package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
