package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
