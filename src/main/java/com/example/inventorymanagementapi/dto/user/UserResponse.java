package com.example.inventorymanagementapi.dto.user;

import com.example.inventorymanagementapi.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private Gender gender;
    private LocalDate birthDate;
    private Boolean active = Boolean.TRUE;
    private String photoUrl;
    private List<String> roles;
}
