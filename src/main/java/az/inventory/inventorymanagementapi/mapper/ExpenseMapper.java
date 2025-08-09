package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.expense.ExpenseCreateRequest;
import az.inventory.inventorymanagementapi.dto.expense.ExpenseResponse;
import az.inventory.inventorymanagementapi.entity.Expense;

public class ExpenseMapper {

    public static Expense toEntity(ExpenseCreateRequest dto) {
        return Expense.builder()
                .title(dto.getTitle())
                .amount(dto.getAmount())
                .expenseDate(dto.getExpenseDate())
                .build();
    }

    public static ExpenseResponse toResponse(Expense entity) {
        return ExpenseResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .amount(entity.getAmount())
                .expenseDate(entity.getExpenseDate())
                .build();
    }
}
