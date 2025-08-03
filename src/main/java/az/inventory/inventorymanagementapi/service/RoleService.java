package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.role.RoleCreateRequest;
import az.inventory.inventorymanagementapi.dto.role.RoleFilterRequest;
import az.inventory.inventorymanagementapi.dto.role.RoleResponse;
import az.inventory.inventorymanagementapi.dto.role.RoleUpdateRequest;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleCreateRequest request);

    RoleResponse updateRole(Long id, RoleUpdateRequest request);

    void deleteRole(Long id);

    RoleResponse getRoleById(Long id);

    List<RoleResponse> getAllRoles();

    List<RoleResponse> filterRoles(RoleFilterRequest filterRequest);
}
