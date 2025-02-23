package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.ExpenseResponseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;

import java.util.List;

public interface ExpenseOrchestrator {
    List<ExpenseResponseDTO> getAllExpensesInGroup(Long groupId);
    ExpenseResponseDTO createExpense(Long groupId, ExpenseRequestDTO expenseRequestDTO);
    ExpenseResponseDTO updateExpense(Long groupId, Long expenseId, ExpenseRequestDTO expenseRequestDTO);
    void deleteExpense(Long groupId, Long expenseId);
}
