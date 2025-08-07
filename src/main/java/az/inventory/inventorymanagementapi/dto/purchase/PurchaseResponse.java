package az.inventory.inventorymanagementapi.dto.purchase;

import az.inventory.inventorymanagementapi.dto.purchaseitem.PurchaseItemResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private LocalDateTime purchaseDate;
    private List<PurchaseItemResponse> items;
}
