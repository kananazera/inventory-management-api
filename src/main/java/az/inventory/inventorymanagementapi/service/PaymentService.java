package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.entity.Purchase;

public interface PaymentService {
    void savePayment(Purchase purchase);
}
