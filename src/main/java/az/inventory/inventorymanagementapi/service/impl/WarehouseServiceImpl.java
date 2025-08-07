package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseCreateRequest;
import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseFilterRequest;
import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseResponse;
import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseUpdateRequest;
import az.inventory.inventorymanagementapi.entity.Warehouse;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.mapper.WarehouseMapper;
import az.inventory.inventorymanagementapi.repository.WarehouseRepository;
import az.inventory.inventorymanagementapi.service.WarehouseService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Override
    public WarehouseResponse createWarehouse(WarehouseCreateRequest request) {
        Warehouse warehouse = WarehouseMapper.toEntity(request);
        return WarehouseMapper.toResponse(warehouseRepository.save(warehouse));
    }

    @Override
    public WarehouseResponse updateWarehouse(Long id, WarehouseUpdateRequest request) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + id));

        if (request.getName() != null) {
            warehouse.setName(request.getName());
        }
        if (request.getPhone() != null) {
            warehouse.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            warehouse.setEmail(request.getEmail());
        }
        if (request.getAddress() != null) {
            warehouse.setAddress(request.getAddress());
        }

        return WarehouseMapper.toResponse(warehouseRepository.save(warehouse));
    }

    @Override
    public void deleteWarehouse(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Warehouse not found with id: " + id);
        }
        warehouseRepository.deleteById(id);
    }

    @Override
    public WarehouseResponse getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                .map(WarehouseMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + id));
    }

    @Override
    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(WarehouseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<WarehouseResponse> filterWarehouses(WarehouseFilterRequest filterRequest) {
        Specification<Warehouse> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getName() != null && !filterRequest.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filterRequest.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return warehouseRepository.findAll(spec).stream()
                .map(WarehouseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
