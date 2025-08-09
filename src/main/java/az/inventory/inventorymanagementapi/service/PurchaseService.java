package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.product.ProductFilterRequest;
import az.inventory.inventorymanagementapi.dto.product.ProductResponse;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseCreateRequest;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseFilterRequest;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseResponse;
import az.inventory.inventorymanagementapi.enums.PurchaseStatus;

import java.util.List;

public interface PurchaseService {
    PurchaseResponse createPurchase(PurchaseCreateRequest request);

    void updateStatus(Long purchaseId, PurchaseStatus status);

    List<PurchaseResponse> getAllPurchases();

    List<PurchaseResponse> filterPurchases(PurchaseFilterRequest filterRequest);
}