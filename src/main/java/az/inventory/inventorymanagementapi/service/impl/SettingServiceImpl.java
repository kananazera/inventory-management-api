package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.setting.SettingResponse;
import az.inventory.inventorymanagementapi.dto.setting.SettingUpdateRequest;
import az.inventory.inventorymanagementapi.entity.Setting;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.mapper.SettingMapper;
import az.inventory.inventorymanagementapi.repository.SettingRepository;
import az.inventory.inventorymanagementapi.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    @Override
    public List<SettingResponse> getAllSettings() {
        return settingRepository.findAll(Sort.by("key")).stream()
                .map(SettingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SettingResponse> updateSettings(List<SettingUpdateRequest> requests) {
        requests.forEach(request -> {
            Setting setting = settingRepository.findByKey(request.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("Setting not found for key: " + request.getKey()));
            setting.setValue(request.getValue());
            settingRepository.save(setting);
        });

        return settingRepository.findAll(Sort.by("key")).stream()
                .map(SettingMapper::toResponse)
                .collect(Collectors.toList());
    }
}
