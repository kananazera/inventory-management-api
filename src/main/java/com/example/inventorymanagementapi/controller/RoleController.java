package com.example.inventorymanagementapi.controller;

import com.example.inventorymanagementapi.dto.role.RoleCreateRequest;
import com.example.inventorymanagementapi.dto.role.RoleFilterRequest;
import com.example.inventorymanagementapi.dto.role.RoleResponse;
import com.example.inventorymanagementapi.dto.role.RoleUpdateRequest;
import com.example.inventorymanagementapi.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponse> create(@Valid @RequestBody(required = false) RoleCreateRequest request) {
        if (request == null) {
            throw new RuntimeException("Request body must not be empty");
        }
        RoleResponse response = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable Long id, @RequestBody RoleUpdateRequest request) {
        RoleResponse response = roleService.updateRole(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAll() {
        List<RoleResponse> list = roleService.getAllRoles();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getById(@PathVariable Long id) {
        RoleResponse response = roleService.getRoleById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted successfully");
    }

    @PostMapping("/filter")
    public ResponseEntity<List<RoleResponse>> filter(@RequestBody RoleFilterRequest filterRequest) {
        List<RoleResponse> list = roleService.filterRoles(filterRequest);
        return ResponseEntity.ok(list);
    }
}
