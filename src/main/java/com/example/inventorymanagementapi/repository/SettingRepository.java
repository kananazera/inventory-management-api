package com.example.inventorymanagementapi.repository;

import com.example.inventorymanagementapi.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
}
