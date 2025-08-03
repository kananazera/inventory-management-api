package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
