package az.inventory.inventorymanagementapi.dto.productbrand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductBrandFilterRequest {

    private String name;
}
