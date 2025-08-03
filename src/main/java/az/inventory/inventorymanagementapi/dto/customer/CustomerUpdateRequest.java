package az.inventory.inventorymanagementapi.dto.customer;

import az.inventory.inventorymanagementapi.enums.ContactType;
import az.inventory.inventorymanagementapi.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerUpdateRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^(\\+994|0)[1-9]\\d{7,8}$", message = "Phone number format is invalid")
    private String phone;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    private Gender gender;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private Boolean active = Boolean.TRUE;

    @NotNull(message = "Contact type required")
    private ContactType contactType;

    private Long tin;
}