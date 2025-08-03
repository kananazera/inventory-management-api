package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
