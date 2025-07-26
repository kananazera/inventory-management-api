package com.example.inventorymanagementapi.mapper;

import com.example.inventorymanagementapi.dto.user.UserCreateRequest;
import com.example.inventorymanagementapi.dto.user.UserResponse;
import com.example.inventorymanagementapi.entity.Role;
import com.example.inventorymanagementapi.entity.User;

import java.util.Set;

public class UserMapper {

    public static User toEntity(UserCreateRequest dto, String photoUrl, Set<Role> roles) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .gender(dto.getGender())
                .birthDate(dto.getBirthDate())
                .active(dto.getActive())
                .photoUrl(photoUrl)
                .roles(roles)
                .build();
    }

    public static UserResponse toResponse(User entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .gender(entity.getGender())
                .birthDate(entity.getBirthDate())
                .active(entity.getActive())
                .photoUrl(entity.getPhotoUrl())
                .roles(entity.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toList())
                .build();
    }
}
