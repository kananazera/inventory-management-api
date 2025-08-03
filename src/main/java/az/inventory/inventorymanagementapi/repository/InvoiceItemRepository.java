package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}
