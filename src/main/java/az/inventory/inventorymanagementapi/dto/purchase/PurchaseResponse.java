package az.inventory.inventorymanagementapi.dto.purchase;

import az.inventory.inventorymanagementapi.dto.purchaseitem.PurchaseItemResponse;
import az.inventory.inventorymanagementapi.enums.PaymentStatus;
import az.inventory.inventorymanagementapi.enums.PaymentType;
import az.inventory.inventorymanagementapi.enums.PurchaseStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseResponse {

    private Long id;
    private Long supplierId;
    private Long warehouseId;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private LocalDate purchaseDate;
    private List<PurchaseItemResponse> items;
    private PaymentStatus paymentStatus;
    private PaymentType paymentType;
    private PurchaseStatus status;
}
