package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.entity.Expense;

import java.util.List;

public interface ExpenseService {

    Expense createExpense(Expense expense);

    List<Expense> getAllExpenses();

    void recordExpenses(List<Expense> expenses);
}
