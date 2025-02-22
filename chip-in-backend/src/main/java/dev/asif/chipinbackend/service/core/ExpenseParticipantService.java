package dev.asif.chipinbackend.service.core;

import dev.asif.chipinbackend.model.Expense;
import dev.asif.chipinbackend.model.ExpenseParticipant;
import dev.asif.chipinbackend.model.User;

import java.util.List;

public interface ExpenseParticipantService {
    ExpenseParticipant getExpenseParticipantById(Long id);
    ExpenseParticipant createExpenseParticipant(ExpenseParticipant expenseParticipant);
    ExpenseParticipant updateExpenseParticipant(Long id, ExpenseParticipant expenseParticipantDetails);
    void deleteExpenseParticipant(Long id);

    ExpenseParticipant getParticipantByExpenseAndUser(Expense expense, User user);
    ExpenseParticipant getOrNewParticipant(Expense expense, User user);
    ExpenseParticipant saveExpenseParticipant(ExpenseParticipant expenseParticipant);
}
