package az.inventory.inventorymanagementapi.dto.productbrand;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductBrandUpdateRequest {

    @NotNull(message = "Name is required")
    private String name;
}
