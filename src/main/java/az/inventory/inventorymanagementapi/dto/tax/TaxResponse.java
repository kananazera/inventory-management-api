package az.inventory.inventorymanagementapi.dto.tax;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TaxResponse {

    private Long id;
    private String name;
    private BigDecimal rate;
}