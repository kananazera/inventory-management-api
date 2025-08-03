package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
