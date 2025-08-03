package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.PurchaseRequest;

public interface PurchaseService {
    void createPurchase(PurchaseRequest request);
}
