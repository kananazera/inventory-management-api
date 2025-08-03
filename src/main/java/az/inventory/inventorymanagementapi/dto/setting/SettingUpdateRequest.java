package az.inventory.inventorymanagementapi.dto.setting;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
public class SettingUpdateRequest {

    @NotBlank(message = "Key is required")
    private String key;

    @NotBlank(message = "Value is required")
    private String value;
}
