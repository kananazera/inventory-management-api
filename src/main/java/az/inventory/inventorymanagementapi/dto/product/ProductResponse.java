package az.inventory.inventorymanagementapi.dto.product;

import az.inventory.inventorymanagementapi.entity.ProductBrand;
import az.inventory.inventorymanagementapi.entity.ProductCategory;
import az.inventory.inventorymanagementapi.entity.ProductUnit;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private Boolean active;
    private ProductCategory category;
    private ProductBrand brand;
    private ProductUnit unit;
    private String imageUrl;
}
