package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.entity.Purchase;
import az.inventory.inventorymanagementapi.entity.PurchaseItem;
import az.inventory.inventorymanagementapi.entity.Transaction;
import az.inventory.inventorymanagementapi.enums.TransactionType;
import az.inventory.inventorymanagementapi.repository.TransactionRepository;
import az.inventory.inventorymanagementapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void recordPurchaseTransaction(Purchase purchase) {
        for (PurchaseItem item : purchase.getItems()) {
            Transaction transaction = Transaction.builder()
                    .purchase(purchase)
                    .type(TransactionType.PURCHASE)
                    .product(item.getProduct())
                    .warehouse(purchase.getWarehouse())
                    .quantity(item.getQuantity())
                    .amount(item.getTotalPrice())
                    .description("Purchase of product ID " + item.getProduct().getId())
                    .transactionDate(LocalDateTime.now())
                    .build();

            transactionRepository.save(transaction);
        }
    }
}
