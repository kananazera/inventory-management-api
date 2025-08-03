package az.inventory.inventorymanagementapi.dto.role;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleCreateRequest {

    @NotNull(message = "Name is required")
    private String name;
}
