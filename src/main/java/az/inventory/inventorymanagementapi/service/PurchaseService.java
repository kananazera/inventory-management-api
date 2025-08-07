package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.purchase.PurchaseCreateRequest;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseResponse;

import java.util.List;

public interface PurchaseService {
    PurchaseResponse createPurchase(PurchaseCreateRequest request);

    List<PurchaseResponse> getAllPurchases();
}