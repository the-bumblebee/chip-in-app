package dev.asif.chipinbackend.service.core.impl;

import dev.asif.chipinbackend.exception.ResourceNotFoundException;
import dev.asif.chipinbackend.model.Expense;
import dev.asif.chipinbackend.model.ExpenseParticipant;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.repository.ExpenseRepository;
import dev.asif.chipinbackend.service.core.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense with id " + id + " not found!"));
    }

    @Override
    public Expense createExpense(String description, BigDecimal totalAmount, Group group, List<ExpenseParticipant> expenseParticipants) {
        Expense expense = new Expense();
        expense.setDescription(description);
        expense.setTotalAmount(totalAmount);
        expense.setGroup(group);
        expense.setParticipants(expenseParticipants);
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, Expense expenseDetails) {
        Expense expense = getExpenseById(id);
        expense.setDescription(expenseDetails.getDescription());
        expense.setTotalAmount(expenseDetails.getTotalAmount());
        expense.setParticipants(expenseDetails.getParticipants());
        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public List<Expense> getExpensesByGroup(Group group) {
        return expenseRepository.findByGroup(group);
    }
}
