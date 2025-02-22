package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.ExpenseResponseDTO;
import dev.asif.chipinbackend.dto.core.ExpenseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;

import java.util.List;

public interface ExpenseManager {
    List<ExpenseResponseDTO> getAllExpensesInGroup(Long groupId);
    ExpenseDTO createExpense(Long groupId, ExpenseRequestDTO expenseRequestDTO);
}
