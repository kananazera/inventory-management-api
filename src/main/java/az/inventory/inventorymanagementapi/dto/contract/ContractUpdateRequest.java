package az.inventory.inventorymanagementapi.dto.contract;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContractUpdateRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    private Long supplierId;

    private Long customerId;

    @AssertTrue(message = "Either supplierId or customerId must be provided")
    public boolean isEitherPartyProvided() {
        return supplierId != null || customerId != null;
    }
}