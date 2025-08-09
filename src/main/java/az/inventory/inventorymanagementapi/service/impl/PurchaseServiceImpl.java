package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.purchase.PurchaseCreateRequest;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseFilterRequest;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseResponse;
import az.inventory.inventorymanagementapi.entity.*;
import az.inventory.inventorymanagementapi.enums.PaymentStatus;
import az.inventory.inventorymanagementapi.enums.PurchaseStatus;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.mapper.PurchaseMapper;
import az.inventory.inventorymanagementapi.repository.*;
import az.inventory.inventorymanagementapi.service.InventoryService;
import az.inventory.inventorymanagementapi.service.PurchaseService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final InventoryService inventoryService;

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

        BigDecimal paidAmount = request.getPaidAmount() != null ? request.getPaidAmount() : BigDecimal.ZERO;

        if (paidAmount.compareTo(BigDecimal.ZERO) > 0) {
            if (request.getPaymentType() == null) {
                throw new IllegalArgumentException("Payment type must be provided when paid amount is greater than zero");
            }

            BigDecimal totalAmount = savedPurchase.getTotalAmount();

            if (paidAmount.compareTo(totalAmount) > 0) {
                throw new IllegalArgumentException("Paid amount cannot be greater than total amount");
            }

            PaymentStatus status;
            if (paidAmount.compareTo(totalAmount) == 0) {
                status = PaymentStatus.PAID;
            } else {
                status = PaymentStatus.PARTIAL;
            }

            Payment payment = Payment.builder()
                    .purchase(savedPurchase)
                    .amount(paidAmount)
                    .paymentType(request.getPaymentType())
                    .paymentStatus(status)
                    .paymentDate(LocalDateTime.now())
                    .build();

            paymentRepository.save(payment);
        }

        if (purchase.getStatus() == PurchaseStatus.COMPLETED) {
            inventoryService.increaseStock(purchase);
        }

        return PurchaseMapper.toResponse(savedPurchase);
    }

    @Override
    public void updateStatus(Long purchaseId, PurchaseStatus status) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found: " + purchaseId));

        purchase.setStatus(status);
        purchaseRepository.save(purchase);

        if (status == PurchaseStatus.COMPLETED) {
            inventoryService.increaseStock(purchase);
        }
    }

    @Override
    public List<PurchaseResponse> filterPurchases(PurchaseFilterRequest filterRequest) {
        Specification<Purchase> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filterRequest.getId()));
            }
            if (filterRequest.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplier").get("id"), filterRequest.getSupplierId()));
            }
            if (filterRequest.getWarehouseId() != null) {
                predicates.add(cb.equal(root.get("warehouse").get("id"), filterRequest.getWarehouseId()));
            }
            if (filterRequest.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filterRequest.getStatus()));
            }

            if (filterRequest.getPurchaseDate() != null) {
                LocalDate filterDate = filterRequest.getPurchaseDate();
                LocalDateTime startOfDay = filterDate.atStartOfDay();
                LocalDateTime endOfDay = filterDate.atTime(23, 59, 59, 999999999);
                predicates.add(cb.between(root.get("purchaseDate"), startOfDay, endOfDay));
            }

            if (filterRequest.getMinTotalAmount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("totalAmount"), filterRequest.getMinTotalAmount()));
            }
            if (filterRequest.getMaxTotalAmount() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("totalAmount"), filterRequest.getMaxTotalAmount()));
            }

            if (filterRequest.getPaymentStatus() != null || filterRequest.getPaymentType() != null) {
                Join<Purchase, Payment> paymentJoin = root.join("payments", JoinType.LEFT);

                if (filterRequest.getPaymentStatus() != null) {
                    predicates.add(cb.equal(paymentJoin.get("paymentStatus"), filterRequest.getPaymentStatus()));
                }
                if (filterRequest.getPaymentType() != null) {
                    predicates.add(cb.equal(paymentJoin.get("paymentType"), filterRequest.getPaymentType()));
                }

                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return purchaseRepository.findAll(spec).stream()
                .map(PurchaseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseResponse> getAllPurchases() {
        return purchaseRepository.findAll()
                .stream()
                .map(PurchaseMapper::toResponse)
                .collect(Collectors.toList());
    }
}