package az.inventory.inventorymanagementapi.dto.purchase;

import az.inventory.inventorymanagementapi.dto.purchaseitem.PurchaseItemResponse;
import az.inventory.inventorymanagementapi.enums.PaymentStatus;
import az.inventory.inventorymanagementapi.enums.PaymentType;
import az.inventory.inventorymanagementapi.enums.PurchaseStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseFilterRequest {

    private Long id;
    private Long supplierId;
    private Long warehouseId;
    private PurchaseStatus status;
    private LocalDate purchaseDate;
    private PaymentStatus paymentStatus;
    private PaymentType paymentType;
    private BigDecimal minTotalAmount;
    private BigDecimal maxTotalAmount;
}
