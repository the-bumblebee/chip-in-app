package dev.asif.chipinbackend.service.core;

import dev.asif.chipinbackend.model.Expense;
import dev.asif.chipinbackend.model.ExpenseParticipant;
import dev.asif.chipinbackend.model.Group;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseService {
    Expense getExpenseById(Long id);
    Expense createExpense(String description, BigDecimal totalAmount, Group group, List<ExpenseParticipant> expenseParticipants);
    Expense updateExpense(Long id, Expense expenseDetails);
    void deleteExpense(Long id);

    List<Expense> getExpensesByGroup(Group group);
}
