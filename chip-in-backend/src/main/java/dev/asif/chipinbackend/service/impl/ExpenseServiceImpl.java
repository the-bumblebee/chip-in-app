package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.ExpenseRequestDTO;
import dev.asif.chipinbackend.dto.ParticipantDTO;
import dev.asif.chipinbackend.dto.PayerDTO;
import dev.asif.chipinbackend.model.Expense;
import dev.asif.chipinbackend.model.ExpenseParticipant;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.repository.ExpenseParticipantRepository;
import dev.asif.chipinbackend.repository.ExpenseRepository;
import dev.asif.chipinbackend.repository.UserRepository;
import dev.asif.chipinbackend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseParticipantRepository expenseParticipantRepository;

    @Autowired
    public ExpenseServiceImpl(UserRepository userRepository, ExpenseRepository expenseRepository, ExpenseParticipantRepository expenseParticipantRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.expenseParticipantRepository = expenseParticipantRepository;
    }

    @Override
    public Expense createExpense(ExpenseRequestDTO request) {
        // Validate payer exists or not
        Double totalPaidAmount = 0.0;
        for (PayerDTO payer : request.getPayers()) {
            User user = userRepository.findById(payer.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + payer.getUserId() + " not found!"));
            // Update totalAmountPaid for this user in group balance
            totalPaidAmount += payer.getPaidAmount();
        }
        // Find total amount
        // Create expense
        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setTotalAmount(totalPaidAmount);
        // Writing to database at the last for consistency

        // processing based on splitType
        switch(request.getSplitType()) {
            case "equal":
                processEqualSplit(expense, request.getParticipants());
                break;
            case "shares":
                processSharesSplit(expense, request.getParticipants());
                break;
            case "percentage":
                processPercentageSplit(expense, request.getParticipants());
                break;
            case "unequal":
                processUnequalSplit(expense, request.getParticipants());
                break;
            default:
                throw new IllegalArgumentException("Invalid split type!");
        }

        expenseRepository.save(expense);
        return expense;
    }

    private void processEqualSplit(Expense expense, List<ParticipantDTO> participants) {
        double shareAmount = expense.getTotalAmount() / (double) participants.size();
        for (ParticipantDTO participant : participants) {
            saveExpenseParticipant(expense, participant.getUserId(), shareAmount);
        }
    }

    private void processSharesSplit(Expense expense, List<ParticipantDTO> participants) {
        int totalShares = participants.stream().mapToInt(ParticipantDTO::getShares).sum();
        for (ParticipantDTO participant : participants) {
            double shareAmount = (participant.getShares() / (double) totalShares) * expense.getTotalAmount();
            saveExpenseParticipant(expense, participant.getUserId(), shareAmount);
        }
    }

    private void processPercentageSplit(Expense expense, List<ParticipantDTO> participants) {
        double totalPercentage = participants.stream().mapToDouble(ParticipantDTO::getPercentage).sum();
        if (totalPercentage != 100) {
            throw new IllegalArgumentException("Percentages must sum up to 100%!");
        }
        for (ParticipantDTO participant : participants) {
            double shareAmount = (participant.getPercentage() / 100.0) * expense.getTotalAmount();
            saveExpenseParticipant(expense, participant.getUserId(), shareAmount);
        }
    }

    private void processUnequalSplit(Expense expense, List<ParticipantDTO> participants) {
        double totalShareAmount = participants.stream().mapToDouble(ParticipantDTO::getShareAmount).sum();
        if (totalShareAmount != expense.getTotalAmount()) {
            throw new IllegalArgumentException("Total share amount must match total paid amount!");
        }
        for (ParticipantDTO participant : participants) {
            saveExpenseParticipant(expense, participant.getUserId(), participant.getShareAmount());
        }
    }

    private void saveExpenseParticipant(Expense expense, Long userId, double shareAmount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId +" not found!"));
        ExpenseParticipant participant = new ExpenseParticipant();
        participant.setUser(user);
        participant.setExpense(expense);
        participant.setShareAmount(shareAmount);
        expenseParticipantRepository.save(participant);
    }
}
