package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.ExpenseRequestDTO;
import dev.asif.chipinbackend.model.Expense;

public interface ExpenseService {
    Expense createExpense(ExpenseRequestDTO request);
}
