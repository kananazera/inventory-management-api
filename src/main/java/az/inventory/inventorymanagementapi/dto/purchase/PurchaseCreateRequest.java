package az.inventory.inventorymanagementapi.dto.purchase;

import az.inventory.inventorymanagementapi.dto.purchaseitem.PurchaseItemCreateRequest;
import az.inventory.inventorymanagementapi.entity.Expense;
import az.inventory.inventorymanagementapi.enums.PaymentStatus;
import az.inventory.inventorymanagementapi.enums.PaymentType;
import az.inventory.inventorymanagementapi.enums.PurchaseStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseCreateRequest {

    @NotNull(message = "Supplier ID cannot be null")
    private Long supplierId;

    @NotNull(message = "Warehouse ID cannot be null")
    private Long warehouseId;

    @NotNull(message = "Purchase date cannot be null")
    private LocalDateTime purchaseDate;

    @NotEmpty(message = "Purchase items cannot be empty")
    private List<PurchaseItemCreateRequest> items;

    private BigDecimal paidAmount;
    private PaymentType paymentType;

    private PurchaseStatus status;
}
