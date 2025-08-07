package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.purchase.PurchaseCreateRequest;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseResponse;
import az.inventory.inventorymanagementapi.entity.Purchase;
import az.inventory.inventorymanagementapi.entity.Supplier;
import az.inventory.inventorymanagementapi.entity.Warehouse;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.mapper.PurchaseMapper;
import az.inventory.inventorymanagementapi.repository.ProductRepository;
import az.inventory.inventorymanagementapi.repository.PurchaseRepository;
import az.inventory.inventorymanagementapi.repository.SupplierRepository;
import az.inventory.inventorymanagementapi.repository.WarehouseRepository;
import az.inventory.inventorymanagementapi.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    // private final InventoryRepository inventoryRepository;
    // private final ExpenseRepository expenseRepository;
    // private final TaxRepository taxRepository;
    // private final PaymentRepository paymentRepository;
    // private final InvoiceRepository invoiceRepository;
    // private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public PurchaseResponse createPurchase(PurchaseCreateRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("At least one purchase item must be selected");
        }

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + request.getSupplierId()));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found: " + request.getWarehouseId()));

        Purchase purchase = PurchaseMapper.toEntity(request, productId ->
                productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId))
        );

        purchase.setSupplier(supplier);
        purchase.setWarehouse(warehouse);

        Purchase savedPurchase = purchaseRepository.save(purchase);

        // eger statusu OK dirse

        // inventory yazmaq
        // expense varsa toplam meblege yazmaq
        // tax varsa toplam meblege yazmaq
        // payment varsa yazmaq
        // invoice yazmaq
        // transaction yazmaq

        // alislari filter elemek yazmamisam.
        // eger varsa borcdan odemek
        // alisin statuslari
        // alisi legv etmek (mueyyen prosesler gedecek mal qaligi azalmali ve s.)

        return PurchaseMapper.toResponse(savedPurchase);
    }

    @Override
    public List<PurchaseResponse> getAllPurchases() {
        return purchaseRepository.findAll()
                .stream()
                .map(PurchaseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
