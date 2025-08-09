package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.product.ProductResponse;
import az.inventory.inventorymanagementapi.dto.product.ProductUpdateRequest;
import az.inventory.inventorymanagementapi.dto.user.UserCreateRequest;
import az.inventory.inventorymanagementapi.dto.user.UserFilterRequest;
import az.inventory.inventorymanagementapi.dto.user.UserResponse;
import az.inventory.inventorymanagementapi.dto.user.UserUpdateRequest;
import az.inventory.inventorymanagementapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponse> create(
            @Valid @ModelAttribute UserCreateRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {
        if (request == null) {
            throw new RuntimeException("Request body must not be empty");
        }
        UserResponse response = userService.createUser(request, photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @ModelAttribute UserUpdateRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {

        UserResponse response = userService.updateUser(id, request, photo);
        return ResponseEntity.ok(response);
    }

    // filteri isteifde etmek
//    @GetMapping
//    public ResponseEntity<List<UserResponse>> getAll() {
//        List<UserResponse> list = userService.getAllUsers();
//        return ResponseEntity.ok(list);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
        ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/filter")
    public ResponseEntity<List<UserResponse>> filter(@RequestBody UserFilterRequest filterRequest) {
        List<UserResponse> list = userService.filterUsers(filterRequest);
        return ResponseEntity.ok(list);
    }
}