package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.ExpenseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;

public interface ExpenseService {
    ExpenseDTO createExpense(Long groupId, ExpenseRequestDTO request);
}
