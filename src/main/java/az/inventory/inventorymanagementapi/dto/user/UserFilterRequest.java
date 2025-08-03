package az.inventory.inventorymanagementapi.dto.user;

import az.inventory.inventorymanagementapi.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class UserFilterRequest {

    private String username;
    private String email;
    private String fullName;
    private String phone;
    private Gender gender;
    private LocalDate birthDate;
    private Boolean active = Boolean.TRUE;
    private Set<Long> roles;
}
