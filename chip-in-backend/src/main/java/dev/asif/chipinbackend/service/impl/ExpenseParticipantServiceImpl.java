package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.exception.ResourceAlreadyExistsException;
import dev.asif.chipinbackend.exception.ResourceNotFoundException;
import dev.asif.chipinbackend.model.Expense;
import dev.asif.chipinbackend.model.ExpenseParticipant;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.repository.ExpenseParticipantRepository;
import dev.asif.chipinbackend.service.ExpenseParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExpenseParticipantServiceImpl implements ExpenseParticipantService {

    private final ExpenseParticipantRepository expenseParticipantRepository;

    @Autowired
    public ExpenseParticipantServiceImpl(ExpenseParticipantRepository expenseParticipantRepository) {
        this.expenseParticipantRepository = expenseParticipantRepository;
    }

    @Override
    public ExpenseParticipant createExpenseParticipant(Expense expense, User user) {
        expenseParticipantRepository.findByExpenseAndUser(expense, user)
                .ifPresent((participant) -> {
                    throw new ResourceAlreadyExistsException("Participant resource exists for the user and the expense.");
                });

        return new ExpenseParticipant(expense, user, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    public ExpenseParticipant getExpenseParticipant(Expense expense, User user) {
        return expenseParticipantRepository.findByExpenseAndUser(expense, user)
                .orElseThrow(() -> new ResourceNotFoundException("Participant resource not found for the user and the expense!"));
    }

    @Override
    public ExpenseParticipant saveExpenseParticipant(ExpenseParticipant participant) {
        return expenseParticipantRepository.save(participant);
    }
}
