package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.ExpenseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;

import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO> getAllExpensesInGroup(Long groupId);
    ExpenseDTO createExpense(Long groupId, ExpenseRequestDTO request);
}
