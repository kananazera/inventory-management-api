package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.entity.Purchase;

public interface InventoryService {
    void increaseStock(Purchase purchase);
}
