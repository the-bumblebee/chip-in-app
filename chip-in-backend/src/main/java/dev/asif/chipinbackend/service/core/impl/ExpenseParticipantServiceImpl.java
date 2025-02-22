package dev.asif.chipinbackend.service.core.impl;

import dev.asif.chipinbackend.exception.ResourceNotFoundException;
import dev.asif.chipinbackend.model.Expense;
import dev.asif.chipinbackend.model.ExpenseParticipant;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.repository.ExpenseParticipantRepository;
import dev.asif.chipinbackend.service.core.ExpenseParticipantService;
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
    public ExpenseParticipant getExpenseParticipantById(Long id) {
        return expenseParticipantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseParticipant with id " + id + " not found!"));
    }

    @Override
    public ExpenseParticipant createExpenseParticipant(ExpenseParticipant expenseParticipant) {
        return expenseParticipantRepository.save(expenseParticipant);
    }

    @Override
    public ExpenseParticipant updateExpenseParticipant(Long id, ExpenseParticipant expenseParticipantDetails) {
        ExpenseParticipant expenseParticipant = getExpenseParticipantById(id);
        expenseParticipant.setPaidAmount(expenseParticipantDetails.getPaidAmount());
        expenseParticipant.setShareAmount(expenseParticipantDetails.getShareAmount());
        return expenseParticipantRepository.save(expenseParticipant);
    }

    @Override
    public void deleteExpenseParticipant(Long id) {
        expenseParticipantRepository.deleteById(id);
    }

    @Override
    public ExpenseParticipant getParticipantByExpenseAndUser(Expense expense, User user) {
        return expenseParticipantRepository.findByExpenseAndUser(expense, user)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseParticipant resource not found for the specific user and expense!"));
    }

    @Override
    public ExpenseParticipant getOrNewParticipant(Expense expense, User user) {
        return expenseParticipantRepository.findByExpenseAndUser(expense, user)
                .orElse(new ExpenseParticipant(expense, user, BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @Override
    public ExpenseParticipant saveExpenseParticipant(ExpenseParticipant expenseParticipant) {
        return expenseParticipantRepository.save(expenseParticipant);
    }
}
