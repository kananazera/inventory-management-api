package az.inventory.inventorymanagementapi.dto.purchaseitem;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItemCreateRequest {

    @NotNull(message = "Product ID boş ola bilməz")
    private Long productId;

    @NotNull(message = "Quantity boş ola bilməz")
    @Min(value = 1, message = "Minimum 1 ədəd olmalıdır")
    private Integer quantity;

    @NotNull(message = "Unit price boş ola bilməz")
    @DecimalMin(value = "0.01", message = "Qiymət sıfırdan böyük olmalıdır")
    private BigDecimal unitPrice;
}
