package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.entity.Purchase;

public interface TransactionService {
    void recordPurchaseTransaction(Purchase purchase);
}
