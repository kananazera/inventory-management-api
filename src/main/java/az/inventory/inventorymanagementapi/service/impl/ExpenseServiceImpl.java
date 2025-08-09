package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.expense.ExpenseCreateRequest;
import az.inventory.inventorymanagementapi.dto.expense.ExpenseFilterRequest;
import az.inventory.inventorymanagementapi.dto.expense.ExpenseResponse;
import az.inventory.inventorymanagementapi.dto.expense.ExpenseUpdateRequest;
import az.inventory.inventorymanagementapi.entity.Expense;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.mapper.ExpenseMapper;
import az.inventory.inventorymanagementapi.repository.ExpenseRepository;
import az.inventory.inventorymanagementapi.service.ExpenseService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Override
    public ExpenseResponse createExpense(ExpenseCreateRequest request) {

        Expense expense = ExpenseMapper.toEntity(request);
        return ExpenseMapper.toResponse(expenseRepository.save(expense));
    }

    @Override
    public ExpenseResponse updateExpense(Long id, ExpenseUpdateRequest request) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        if (request.getTitle() != null) {
            expense.setTitle(request.getTitle());
        }

        if (request.getAmount() != null) {
            expense.setAmount(request.getAmount());
        }

        if (request.getExpenseDate() != null) {
            expense.setExpenseDate(request.getExpenseDate());
        }

        return ExpenseMapper.toResponse(expenseRepository.save(expense));
    }

    @Override
    public void deleteExpense(Long id) {
        Expense Unit = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        expenseRepository.delete(Unit);
    }

    @Override
    public ExpenseResponse getExpenseById(Long id) {
        Expense Unit = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("expense not found with id: " + id));
        return ExpenseMapper.toResponse(Unit);
    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(ExpenseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseResponse> filterExpenses(ExpenseFilterRequest filterRequest) {
        Specification<Expense> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getTitle() != null && !filterRequest.getTitle().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + filterRequest.getTitle().toLowerCase() + "%"));
            }

            if (filterRequest.getExpenseDate() != null) {
                predicates.add(cb.equal(root.get("expenseDate"), filterRequest.getExpenseDate()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return expenseRepository.findAll(spec).stream()
                .map(ExpenseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
