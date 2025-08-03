package az.inventory.inventorymanagementapi.dto.productunit;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUnitCreateRequest {

    @NotNull(message = "Name is required")
    private String name;
}
