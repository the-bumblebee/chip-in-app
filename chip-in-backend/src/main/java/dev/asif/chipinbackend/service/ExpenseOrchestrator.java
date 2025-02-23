package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.ExpenseDetailResponseDTO;
import dev.asif.chipinbackend.dto.ExpenseResponseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;

import java.util.List;

public interface ExpenseOrchestrator {
    List<ExpenseResponseDTO> getAllExpensesInGroup(Long groupId);
    ExpenseDetailResponseDTO createExpense(Long groupId, ExpenseRequestDTO expenseRequestDTO);
    ExpenseDetailResponseDTO updateExpense(Long groupId, Long expenseId, ExpenseRequestDTO expenseRequestDTO);
    void deleteExpense(Long groupId, Long expenseId);
}
