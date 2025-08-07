package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.entity.Expense;
import az.inventory.inventorymanagementapi.repository.ExpenseRepository;
import az.inventory.inventorymanagementapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Override
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public void recordExpenses(List<Expense> expenses) {
        if (expenses == null || expenses.isEmpty()) {
            return;
        }
        expenseRepository.saveAll(expenses);
    }
}
