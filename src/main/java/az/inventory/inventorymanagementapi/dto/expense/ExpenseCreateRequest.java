package az.inventory.inventorymanagementapi.dto.expense;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ExpenseCreateRequest {

    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Date is required")
    private LocalDate expenseDate;
}
