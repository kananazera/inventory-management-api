package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.role.RoleCreateRequest;
import az.inventory.inventorymanagementapi.dto.role.RoleFilterRequest;
import az.inventory.inventorymanagementapi.dto.role.RoleResponse;
import az.inventory.inventorymanagementapi.dto.role.RoleUpdateRequest;
import az.inventory.inventorymanagementapi.entity.Role;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.mapper.RoleMapper;
import az.inventory.inventorymanagementapi.repository.RoleRepository;
import az.inventory.inventorymanagementapi.service.RoleService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponse createRole(RoleCreateRequest request) {
        if (roleRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Role name already exists");
        }
        Role role = RoleMapper.toEntity(request);
        return RoleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse updateRole(Long id, RoleUpdateRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        if (roleRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new RuntimeException("Role name already exists");
        }

        if (request.getName() != null) {
            role.setName(request.getName());
        }

        return RoleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        roleRepository.delete(role);
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return RoleMapper.toResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleResponse> filterRoles(RoleFilterRequest filterRequest) {
        Specification<Role> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getName() != null && !filterRequest.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filterRequest.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return roleRepository.findAll(spec).stream()
                .map(RoleMapper::toResponse)
                .collect(Collectors.toList());
    }
}
