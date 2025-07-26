package com.example.inventorymanagementapi.service;

import com.example.inventorymanagementapi.dto.user.UserCreateRequest;
import com.example.inventorymanagementapi.dto.user.UserFilterRequest;
import com.example.inventorymanagementapi.dto.user.UserResponse;
import com.example.inventorymanagementapi.dto.user.UserUpdateRequest;
import io.jsonwebtoken.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request, MultipartFile photo) throws IOException, java.io.IOException;

    UserResponse updateUser(Long id, UserUpdateRequest request, MultipartFile photo) throws IOException, java.io.IOException;

    void deleteUser(Long id);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    List<UserResponse> filterUsers(UserFilterRequest filterRequest);
}