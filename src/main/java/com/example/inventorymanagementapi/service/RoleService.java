package com.example.inventorymanagementapi.service;

import com.example.inventorymanagementapi.dto.role.RoleCreateRequest;
import com.example.inventorymanagementapi.dto.role.RoleFilterRequest;
import com.example.inventorymanagementapi.dto.role.RoleResponse;
import com.example.inventorymanagementapi.dto.role.RoleUpdateRequest;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleCreateRequest request);

    RoleResponse updateRole(Long id, RoleUpdateRequest request);

    void deleteRole(Long id);

    RoleResponse getRoleById(Long id);

    List<RoleResponse> getAllRoles();

    List<RoleResponse> filterRoles(RoleFilterRequest filterRequest);
}
