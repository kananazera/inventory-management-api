package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.setting.SettingResponse;
import az.inventory.inventorymanagementapi.dto.setting.SettingUpdateRequest;
import az.inventory.inventorymanagementapi.service.SettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @GetMapping
    public ResponseEntity<List<SettingResponse>> getAll() {
        return ResponseEntity.ok(settingService.getAllSettings());
    }

    @PutMapping
    public ResponseEntity<List<SettingResponse>> updateAll(@RequestBody @Valid List<SettingUpdateRequest> requests) {
        return ResponseEntity.ok(settingService.updateSettings(requests));
    }

}
