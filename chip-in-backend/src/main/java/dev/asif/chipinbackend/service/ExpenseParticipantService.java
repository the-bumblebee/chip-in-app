package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.model.Expense;
import dev.asif.chipinbackend.model.ExpenseParticipant;
import dev.asif.chipinbackend.model.User;

public interface ExpenseParticipantService {
    ExpenseParticipant createExpenseParticipant(Expense expense, User user);
    ExpenseParticipant getExpenseParticipant(Expense expense, User user);
    ExpenseParticipant saveExpenseParticipant(ExpenseParticipant participant);
}
