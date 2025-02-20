package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.ExpenseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;
import dev.asif.chipinbackend.dto.ParticipantDTO;
import dev.asif.chipinbackend.dto.PayerDTO;
import dev.asif.chipinbackend.model.*;
import dev.asif.chipinbackend.repository.*;
import dev.asif.chipinbackend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseParticipantRepository expenseParticipantRepository;
    private final UserGroupBalanceRepository userGroupBalanceRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public ExpenseServiceImpl(UserRepository userRepository,
                              ExpenseRepository expenseRepository,
                              ExpenseParticipantRepository expenseParticipantRepository,
                              UserGroupBalanceRepository userGroupBalanceRepository,
                              GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.expenseParticipantRepository = expenseParticipantRepository;
        this.userGroupBalanceRepository = userGroupBalanceRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ExpenseDTO createExpense(Long groupId, ExpenseRequestDTO request) {
        // Validate group
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group with id " + groupId + " not found!"));
        // Validate payer exists or not
        for (PayerDTO payer : request.getPayers()) {
            User user = userRepository.findById(payer.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + payer.getUserId() + " not found!"));
        }
        BigDecimal totalPaidAmount = request.getPayers().stream()
                .map(PayerDTO::getPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        Expense expense = new Expense();
        expense.setGroup(group);
        expense.setDescription(request.getDescription());
        expense.setTotalAmount(totalPaidAmount);

        expenseRepository.save(expense);

        for (PayerDTO payer : request.getPayers()) {
            User user = userRepository.findById(payer.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + payer.getUserId() + " not found!"));
            saveExpenseParticipant(expense, user, payer.getPaidAmount(), BigDecimal.ZERO);
            updateUserGroupBalance(user, group, payer.getPaidAmount(), BigDecimal.ZERO);
        }
        // Find total amount
        // Create expense
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

        return new ExpenseDTO(expense);
    }

    private void processEqualSplit(Expense expense, List<ParticipantDTO> participants) {
        BigDecimal shareAmount = expense.getTotalAmount().divide(BigDecimal.valueOf(participants.size()), 2, RoundingMode.HALF_UP);
        for (ParticipantDTO participant : participants) {
            User user = userRepository.findById(participant.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + participant.getUserId() + " not found!"));
            saveExpenseParticipant(expense, user, BigDecimal.ZERO, shareAmount);
            updateUserGroupBalance(user, expense.getGroup(), BigDecimal.ZERO, shareAmount);
        }
    }

    private void processSharesSplit(Expense expense, List<ParticipantDTO> participants) {
        int totalShares = participants.stream().mapToInt(ParticipantDTO::getShares).sum();
        for (ParticipantDTO participant : participants) {
            BigDecimal shareAmount = expense.getTotalAmount().multiply(BigDecimal.valueOf(participant.getShares() / (double) totalShares)).setScale(2, RoundingMode.HALF_UP);
            User user = userRepository.findById(participant.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + participant.getUserId() + " not found!"));
            saveExpenseParticipant(expense, user, BigDecimal.ZERO, shareAmount);
            updateUserGroupBalance(user, expense.getGroup(), BigDecimal.ZERO, shareAmount);
        }
    }

    private void processPercentageSplit(Expense expense, List<ParticipantDTO> participants) {
        double totalPercentage = participants.stream().mapToDouble(ParticipantDTO::getPercentage).sum();
        if (totalPercentage != 100) {
            throw new IllegalArgumentException("Percentages must sum up to 100%!");
        }
        for (ParticipantDTO participant : participants) {
            BigDecimal shareAmount = expense.getTotalAmount().multiply(BigDecimal.valueOf(participant.getPercentage() / 100.0)).setScale(2, RoundingMode.HALF_UP);
            User user = userRepository.findById(participant.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + participant.getUserId() + " not found!"));
            saveExpenseParticipant(expense, user, BigDecimal.ZERO, shareAmount);
            updateUserGroupBalance(user, expense.getGroup(), BigDecimal.ZERO, shareAmount);
        }
    }

    private void processUnequalSplit(Expense expense, List<ParticipantDTO> participants) {
        BigDecimal totalShareAmount = participants.stream().map(ParticipantDTO::getShareAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalShareAmount.compareTo(expense.getTotalAmount()) != 0) {
            throw new IllegalArgumentException("Total share amount must match total paid amount!");
        }
        for (ParticipantDTO participant : participants) {
            User user = userRepository.findById(participant.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + participant.getUserId() + " not found!"));
            saveExpenseParticipant(expense, user, BigDecimal.ZERO, participant.getShareAmount());
            updateUserGroupBalance(user, expense.getGroup(), BigDecimal.ZERO, participant.getShareAmount());
        }
    }

    private void saveExpenseParticipant(Expense expense, User user, BigDecimal paidAmount, BigDecimal shareAmount) {
        ExpenseParticipant participant = expenseParticipantRepository.findByExpenseAndUser(expense, user)
                .orElse(new ExpenseParticipant(expense, user, BigDecimal.ZERO, BigDecimal.ZERO));
        if (paidAmount.compareTo(BigDecimal.ZERO) != 0) {
            participant.setPaidAmount(paidAmount);
        }
        if (shareAmount.compareTo(BigDecimal.ZERO) != 0) {
            participant.setShareAmount(shareAmount);
        }
        expense.addParticipant(participant);
        expenseParticipantRepository.save(participant);
    }

    private void updateUserGroupBalance(User user, Group group, BigDecimal paidAmount, BigDecimal shareAmount) {
        UserGroupBalance balance = userGroupBalanceRepository.findByUserAndGroup(user, group)
                .orElse(new UserGroupBalance(user, group, BigDecimal.ZERO, BigDecimal.ZERO));
        balance.updateBalance(paidAmount, shareAmount);
        userGroupBalanceRepository.save(balance);
    }
}
