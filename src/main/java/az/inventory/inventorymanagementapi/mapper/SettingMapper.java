package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.setting.SettingResponse;
import az.inventory.inventorymanagementapi.entity.Setting;

public class SettingMapper {

    public static SettingResponse toResponse(Setting setting) {
        return SettingResponse.builder()
                .id(setting.getId())
                .key(setting.getKey())
                .value(setting.getValue())
                .description(setting.getDescription())
                .build();
    }
}
