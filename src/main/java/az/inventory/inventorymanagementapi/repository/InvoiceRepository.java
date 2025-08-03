package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
