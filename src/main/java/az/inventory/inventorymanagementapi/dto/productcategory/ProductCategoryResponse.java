package az.inventory.inventorymanagementapi.dto.productcategory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryResponse {

    private Long id;
    private String name;
}