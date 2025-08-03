package az.inventory.inventorymanagementapi.dto.setting;

import lombok.*;

@Data
@Builder
public class SettingResponse {
    private Long id;
    private String key;
    private String value;
    private String description;
}
