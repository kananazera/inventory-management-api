package az.inventory.inventorymanagementapi.dto.tax;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaxFilterRequest {

    private String name;
}
