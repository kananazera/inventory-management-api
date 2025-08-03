package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.PurchaseItemRequest;
import az.inventory.inventorymanagementapi.dto.PurchaseRequest;
import az.inventory.inventorymanagementapi.entity.*;
import az.inventory.inventorymanagementapi.enums.TransactionType;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.repository.*;
import az.inventory.inventorymanagementapi.service.PurchaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final InventoryRepository inventoryRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    @Override
    public void createPurchase(PurchaseRequest request) {
        // 1. Təchizatçı tapılır
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + request.getSupplierId()));

        // 2. Anbar tapılır
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + request.getWarehouseId()));

        // 3. Purchase yaradılır
        Purchase purchase = Purchase.builder()
                .supplier(supplier)
                .warehouse(warehouse)
                .purchaseDate(LocalDateTime.now())
                .totalAmount(request.getTotalAmount())
                .build();
        purchaseRepository.save(purchase);

        // 4. Hər bir məhsul üçün PurchaseItem və Inventory yaradılır/yenilənir
        for (PurchaseItemRequest itemRequest : request.getItems()) {

            // Məhsul tapılır
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemRequest.getProductId()));

            // PurchaseItem yaradılır
            PurchaseItem item = PurchaseItem.builder()
                    .purchase(purchase)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(itemRequest.getPrice())
                    .build();
            purchaseItemRepository.save(item);

            // Inventory tapılır və ya yaradılır
            Inventory inventory = inventoryRepository
                    .findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
                    .orElseGet(() -> Inventory.builder()
                            .product(product)
                            .warehouse(warehouse)
                            .quantity(0)
                            .build());

            inventory.setQuantity(inventory.getQuantity() + itemRequest.getQuantity());
            inventoryRepository.save(inventory);
        }

        // 5. Invoice yaradılır
        Invoice invoice = Invoice.builder()
                .purchase(purchase)
                .invoiceNumber("INV-" + System.currentTimeMillis())
                .invoiceDate(LocalDateTime.now())
                .totalAmount(request.getTotalAmount())
                .build();
        invoiceRepository.save(invoice);

        // 6. Payment varsa, qeyd olunur
        if (request.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
            Payment payment = Payment.builder()
                    .purchase(purchase)
                    .amount(request.getPaidAmount())
                    .paymentDate(LocalDateTime.now())
                    .paymentType(request.getPaymentType())
                    .build();
            paymentRepository.save(payment);
        }

        // 7. Transaction yaradılır
        Transaction transaction = Transaction.builder()
                .type(TransactionType.PURCHASE)
                .purchase(purchase)
                .amount(request.getTotalAmount())
                .transactionDate(LocalDateTime.now())
                .description("Purchase from supplier ID: " + supplier.getId())
                .build();
        transactionRepository.save(transaction);
    }
}