package az.inventory.inventorymanagementapi.dto.supplier;

import az.inventory.inventorymanagementapi.enums.ContactType;
import az.inventory.inventorymanagementapi.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SupplierResponse {

    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private Gender gender;
    private LocalDate birthDate;
    private Boolean active = Boolean.TRUE;
    private ContactType contactType;
    private Long tin;
}
