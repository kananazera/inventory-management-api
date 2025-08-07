package az.inventory.inventorymanagementapi.dto.warehouse;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseCreateRequest {

    @NotNull(message = "Name is required")
    private String name;

    @Pattern(regexp = "^$|^(\\+994|0)[1-9]\\d{7,8}$", message = "Phone number format is invalid")
    private String phone;

    @Email(message = "Email format is invalid")
    private String email;

    private String address;
}
