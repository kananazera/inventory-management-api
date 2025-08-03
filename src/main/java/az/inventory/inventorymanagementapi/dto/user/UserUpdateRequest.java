package az.inventory.inventorymanagementapi.dto.user;

import az.inventory.inventorymanagementapi.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class UserUpdateRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    private String fullName;

    @Pattern(regexp = "^(\\+994|0)[1-9]\\d{7,8}$", message = "Phone number format is invalid")
    private String phone;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    private Gender gender;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private Boolean active = Boolean.TRUE;

    @NotEmpty(message = "At least one role must be assigned")
    @Size(max = 5, message = "A user cannot have more than 5 roles")
    private Set<Long> roles;
}