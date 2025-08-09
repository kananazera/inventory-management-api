package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.purchase.PurchaseCreateRequest;
import az.inventory.inventorymanagementapi.dto.purchaseitem.PurchaseItemCreateRequest;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseResponse;
import az.inventory.inventorymanagementapi.dto.purchaseitem.PurchaseItemResponse;
import az.inventory.inventorymanagementapi.entity.Payment;
import az.inventory.inventorymanagementapi.entity.Purchase;
import az.inventory.inventorymanagementapi.entity.PurchaseItem;
import az.inventory.inventorymanagementapi.entity.Product;
import az.inventory.inventorymanagementapi.enums.PurchaseStatus;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PurchaseMapper {

    public Purchase toEntity(PurchaseCreateRequest request, ProductLookup productLookup) {
        List<PurchaseItem> items = request.getItems().stream()
                .map(itemReq -> toEntity(itemReq, productLookup))
                .collect(Collectors.toList());

        Purchase purchase = Purchase.builder()
                .purchaseDate(request.getPurchaseDate())
                .items(items)
                .totalAmount(
                        items.stream()
                                .map(PurchaseItem::getTotalPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                )
                .paidAmount(request.getPaidAmount())
                .status(request.getStatus() != null ? request.getStatus() : PurchaseStatus.PENDING)
                .build();

        items.forEach(item -> item.setPurchase(purchase));

        return purchase;
    }

    public PurchaseItem toEntity(PurchaseItemCreateRequest request, ProductLookup productLookup) {
        Product product = productLookup.findById(request.getProductId());
        BigDecimal totalPrice = request.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        return PurchaseItem.builder()
                .product(product)
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .totalPrice(totalPrice)
                .build();
    }

    public PurchaseResponse toResponse(Purchase purchase) {
        List<PurchaseItemResponse> items = purchase.getItems().stream()
                .map(PurchaseMapper::toResponse)
                .collect(Collectors.toList());

        Payment lastPayment = purchase.getPayments() == null || purchase.getPayments().isEmpty()
                ? null
                : purchase.getPayments().stream()
                .max(Comparator.comparing(Payment::getPaymentDate))
                .orElse(null);

        return PurchaseResponse.builder()
                .id(purchase.getId())
                .supplierId(purchase.getSupplier().getId())
                .warehouseId(purchase.getWarehouse().getId())
                .purchaseDate(purchase.getPurchaseDate())
                .totalAmount(purchase.getTotalAmount())
                .paidAmount(purchase.getPaidAmount())
                .status(purchase.getStatus())
                .paymentStatus(lastPayment != null ? lastPayment.getPaymentStatus() : null)
                .paymentType(lastPayment != null ? lastPayment.getPaymentType() : null)
                .items(items)
                .build();
    }

    public PurchaseItemResponse toResponse(PurchaseItem item) {
        return PurchaseItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }

    public interface ProductLookup {
        Product findById(Long id);
    }
}
