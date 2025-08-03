package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.setting.SettingResponse;
import az.inventory.inventorymanagementapi.dto.setting.SettingUpdateRequest;

import java.util.List;

public interface SettingService {
    List<SettingResponse> getAllSettings();
    List<SettingResponse> updateSettings(List<SettingUpdateRequest> requests);
}


