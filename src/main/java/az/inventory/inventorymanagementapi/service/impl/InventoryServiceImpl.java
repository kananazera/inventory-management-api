package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.entity.Inventory;
import az.inventory.inventorymanagementapi.entity.Purchase;
import az.inventory.inventorymanagementapi.entity.PurchaseItem;
import az.inventory.inventorymanagementapi.repository.InventoryRepository;
import az.inventory.inventorymanagementapi.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public void increaseStock(Purchase purchase) {
        for (PurchaseItem item : purchase.getItems()) {
            inventoryRepository.findByProductAndWarehouse(item.getProduct(), purchase.getWarehouse())
                    .ifPresentOrElse(
                            inventory -> {
                                inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
                                inventoryRepository.save(inventory);
                            },
                            () -> {
                                Inventory newInventory = Inventory.builder()
                                        .product(item.getProduct())
                                        .warehouse(purchase.getWarehouse())
                                        .quantity(item.getQuantity())
                                        .build();
                                inventoryRepository.save(newInventory);
                            }
                    );
        }
    }
}