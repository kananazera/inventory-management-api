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

        // alislari filter elemek yazmaq.
        // eger varsa borcdan odemek tam ve ya hisseli, (purchase update etmek qaliq borcu.) (payments table dan purchase id ye gore butun odenislere baxmaq)
        // alisin statuslari
        // alisi geri qaytarmaq (mueyyen prosesler gedecek mal qaligi azalmali ve s.)

        // odenilen meblegin deyeri 0 yox placeholder 0 olsun
        // mehsul secenden sonra vahid qiymet mehsulun enpointinden gelsin amma deyismek olsun

        // inventory bolmesi elave etmek
        // payments bolmesi elave etmek
        // invoice bolmesi elave etmek
        // expense bolmesi elave etmek
        // odenis meblegi toplam meblegden coxdursa exception atsin
        // odenis meblegi 0 dan kicikdirse exception atsin

        // sonda butun bu emeliyyatalr @transactional ile aparilsin

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
