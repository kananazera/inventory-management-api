package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.expense.ExpenseCreateRequest;
import az.inventory.inventorymanagementapi.dto.expense.ExpenseFilterRequest;
import az.inventory.inventorymanagementapi.dto.expense.ExpenseResponse;
import az.inventory.inventorymanagementapi.dto.expense.ExpenseUpdateRequest;

import java.util.List;

public interface ExpenseService {

    ExpenseResponse createExpense(ExpenseCreateRequest request);

    ExpenseResponse updateExpense(Long id, ExpenseUpdateRequest request);

    void deleteExpense(Long id);

    ExpenseResponse getExpenseById(Long id);

    List<ExpenseResponse> getAllExpenses();

    List<ExpenseResponse> filterExpenses(ExpenseFilterRequest filterRequest);
}