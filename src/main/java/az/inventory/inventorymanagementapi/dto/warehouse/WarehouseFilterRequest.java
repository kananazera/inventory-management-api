package az.inventory.inventorymanagementapi.dto.warehouse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseFilterRequest {

    private String name;
}
