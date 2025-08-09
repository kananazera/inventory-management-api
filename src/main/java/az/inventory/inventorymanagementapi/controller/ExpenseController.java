package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.expense.ExpenseCreateRequest;
import az.inventory.inventorymanagementapi.dto.expense.ExpenseFilterRequest;
import az.inventory.inventorymanagementapi.dto.expense.ExpenseResponse;
import az.inventory.inventorymanagementapi.dto.expense.ExpenseUpdateRequest;
import az.inventory.inventorymanagementapi.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponse> create(@Valid @RequestBody ExpenseCreateRequest request) {
        ExpenseResponse response = expenseService.createExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> update(@PathVariable Long id, @RequestBody ExpenseUpdateRequest request) {
        ExpenseResponse response = expenseService.updateExpense(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAll() {
        List<ExpenseResponse> list = expenseService.getAllExpenses();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getById(@PathVariable Long id) {
        ExpenseResponse response = expenseService.getExpenseById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ExpenseResponse>> filter(@RequestBody ExpenseFilterRequest filterRequest) {
        List<ExpenseResponse> list = expenseService.filterExpenses(filterRequest);
        return ResponseEntity.ok(list);
    }
}