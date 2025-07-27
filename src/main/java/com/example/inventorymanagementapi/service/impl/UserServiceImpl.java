package com.example.inventorymanagementapi.service.impl;

import com.example.inventorymanagementapi.dto.user.UserCreateRequest;
import com.example.inventorymanagementapi.dto.user.UserFilterRequest;
import com.example.inventorymanagementapi.dto.user.UserResponse;
import com.example.inventorymanagementapi.dto.user.UserUpdateRequest;
import com.example.inventorymanagementapi.entity.Role;
import com.example.inventorymanagementapi.entity.User;
import com.example.inventorymanagementapi.exception.ResourceNotFoundException;
import com.example.inventorymanagementapi.mapper.UserMapper;
import com.example.inventorymanagementapi.repository.RoleRepository;
import com.example.inventorymanagementapi.repository.UserRepository;
import com.example.inventorymanagementapi.service.FileStorageService;
import com.example.inventorymanagementapi.service.UserService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    @Override
    public UserResponse createUser(UserCreateRequest request, MultipartFile photo) throws IOException {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String photoUrl = null;
        if (photo != null && !photo.isEmpty()) {
            photoUrl = fileStorageService.storeFile(photo);
        }

        Set<Role> roles = roleRepository.findAllById(request.getRoles()).stream()
                .collect(Collectors.toSet());
        if (roles.isEmpty()) {
            throw new ResourceNotFoundException("No valid roles found for the given IDs");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = UserMapper.toEntity(request, photoUrl, roles);
        user.setPassword(encodedPassword);

        String imageUrl = null;
        if (photo != null && !photo.isEmpty()) {
            imageUrl = fileStorageService.storeFile(photo);
        }

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request, MultipartFile photo) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (request.getUsername() != null && !request.getUsername().equalsIgnoreCase(user.getUsername())) {
            if (userRepository.existsByUsernameIgnoreCaseAndIdNot(request.getUsername(), id)) {
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }

        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }

        if (request.getBirthDate() != null) {
            user.setBirthDate(request.getBirthDate());
        }

        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }

        if (photo != null && !photo.isEmpty()) {
            deletePhotoFile(user.getPhotoUrl());
            String imageUrl = fileStorageService.storeFile(photo);
            user.setPhotoUrl(imageUrl);
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Role> roles = roleRepository.findAllById(request.getRoles()).stream()
                    .collect(Collectors.toSet());
            if (roles.isEmpty()) {
                throw new ResourceNotFoundException("No valid roles found for the given IDs");
            }
            user.setRoles(roles);
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        deletePhotoFile(user.getPhotoUrl());

        userRepository.delete(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> filterUsers(UserFilterRequest filterRequest) {
        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getUsername() != null && !filterRequest.getUsername().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("username")),
                        "%" + filterRequest.getUsername().toLowerCase() + "%"));
            }

            if (filterRequest.getEmail() != null && !filterRequest.getEmail().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("email")),
                        "%" + filterRequest.getEmail().toLowerCase() + "%"));
            }

            if (filterRequest.getFullName() != null && !filterRequest.getFullName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("fullName")),
                        "%" + filterRequest.getFullName().toLowerCase() + "%"));
            }

            if (filterRequest.getPhone() != null && !filterRequest.getPhone().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("phone")),
                        "%" + filterRequest.getPhone().toLowerCase() + "%"));
            }

            if (filterRequest.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), filterRequest.getGender()));
            }

            if (filterRequest.getBirthDate() != null) {
                predicates.add(cb.equal(root.get("birthDate"), filterRequest.getBirthDate()));
            }

            if (filterRequest.getActive() != null) {
                predicates.add(cb.equal(root.get("active"), filterRequest.getActive()));
            }

            if (filterRequest.getRoles() != null && !filterRequest.getRoles().isEmpty()) {
                Join<User, Role> roleJoin = root.join("roles", JoinType.INNER);
                predicates.add(roleJoin.get("id").in(filterRequest.getRoles()));
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return userRepository.findAll(spec).stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    private void deletePhotoFile(String photoUrl) {
        if (photoUrl == null || photoUrl.isBlank()) return;

        try {
            String filename = photoUrl.substring(photoUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get(fileStorageService.getUploadDir(), filename);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            System.err.println("Could not delete file: " + photoUrl + " -> " + e.getMessage());
        }
    }
}
