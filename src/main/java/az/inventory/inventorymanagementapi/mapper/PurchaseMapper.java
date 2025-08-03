package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.PurchaseRequest;
import az.inventory.inventorymanagementapi.entity.Purchase;
import az.inventory.inventorymanagementapi.entity.Supplier;
import az.inventory.inventorymanagementapi.entity.Warehouse;

import java.time.LocalDateTime;

public class PurchaseMapper {

    public static Purchase toEntity(PurchaseRequest request, Supplier supplier, Warehouse warehouse) {
        return Purchase.builder()
                .supplier(supplier)
                .warehouse(warehouse)
                .purchaseDate(LocalDateTime.now())
                .totalAmount(request.getTotalAmount())
                .build();
    }
}

