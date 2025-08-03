package az.inventory.inventorymanagementapi.dto;

import az.inventory.inventorymanagementapi.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequest {

    @NotNull
    private Long supplierId;

    @NotNull
    private Long warehouseId;

    @NotNull
    @Positive
    private BigDecimal totalAmount;

    @NotNull
    @Positive
    private BigDecimal paidAmount;

    @NotNull
    private PaymentType paymentType;

    @NotNull
    private List<PurchaseItemRequest> items;
}
