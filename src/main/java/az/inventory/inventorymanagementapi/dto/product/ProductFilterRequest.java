package az.inventory.inventorymanagementapi.dto.product;

import az.inventory.inventorymanagementapi.dto.productcategory.ProductCategoryFilterRequest;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ProductFilterRequest {

    private String name;
    private BigDecimal price;
    private String sku;
    private ProductCategoryFilterRequest category;
    private Long brandId;
    private Long unitId;
    private Boolean active;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
