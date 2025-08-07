package az.inventory.inventorymanagementapi.dto.tax;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TaxUpdateRequest {

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Rate is required")
    private BigDecimal rate;
}
