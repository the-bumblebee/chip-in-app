package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.core.ExpenseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;
import dev.asif.chipinbackend.model.Expense;

import java.util.List;

public interface ExpenseHandlerService {
    Expense getExpenseById(Long id);
    List<ExpenseDTO> getAllExpensesInGroup(Long groupId);
    ExpenseDTO createExpense(Long groupId, ExpenseRequestDTO request);
}
