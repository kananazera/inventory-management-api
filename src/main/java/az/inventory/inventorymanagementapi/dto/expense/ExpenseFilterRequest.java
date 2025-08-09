package az.inventory.inventorymanagementapi.dto.expense;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class ExpenseFilterRequest {

    private String title;
    private Date expenseDate;
}
