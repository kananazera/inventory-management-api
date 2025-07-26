package com.example.inventorymanagementapi.service.impl;

import com.example.inventorymanagementapi.dto.role.RoleCreateRequest;
import com.example.inventorymanagementapi.dto.role.RoleFilterRequest;
import com.example.inventorymanagementapi.dto.role.RoleResponse;
import com.example.inventorymanagementapi.dto.role.RoleUpdateRequest;
import com.example.inventorymanagementapi.entity.Role;
import com.example.inventorymanagementapi.exception.ResourceNotFoundException;
import com.example.inventorymanagementapi.mapper.RoleMapper;
import com.example.inventorymanagementapi.repository.RoleRepository;
import com.example.inventorymanagementapi.service.RoleService;
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
