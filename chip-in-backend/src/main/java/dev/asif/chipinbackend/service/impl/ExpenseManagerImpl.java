package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.ExpenseResponseDTO;
import dev.asif.chipinbackend.dto.core.ExpenseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;
import dev.asif.chipinbackend.dto.ParticipantDTO;
import dev.asif.chipinbackend.dto.PayerDTO;
import dev.asif.chipinbackend.model.*;
import dev.asif.chipinbackend.service.*;
import dev.asif.chipinbackend.service.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExpenseManagerImpl implements ExpenseManager {

    private final UserService userService;
    private final GroupService groupService;
    private final ExpenseService expenseService;
    private final ExpenseParticipantService expenseParticipantService;
    private final UserGroupBalanceService userGroupBalanceService;

    @Autowired
    public ExpenseManagerImpl(UserService userService,
                              GroupService groupService,
                              ExpenseService expenseService,
                              ExpenseParticipantService expenseParticipantService,
                              UserGroupBalanceService userGroupBalanceService) {
        this.userService = userService;
        this.groupService = groupService;
        this.expenseService = expenseService;
        this.expenseParticipantService = expenseParticipantService;
        this.userGroupBalanceService = userGroupBalanceService;
    }

    @Override
    public List<ExpenseResponseDTO> getAllExpensesInGroup(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return expenseService.getExpensesByGroup(group).stream()
                .map(ExpenseResponseDTO::new)
                .toList();
    }

    @Override
    public ExpenseDTO createExpense(Long groupId, ExpenseRequestDTO expenseRequestDTO) {
        // Validate group
        Group group = groupService.getGroupById(groupId);

        // Validate payer exists or not
        for (PayerDTO payer : expenseRequestDTO.getPayers()) {
            User user = userService.getUserById(payer.getUserId());
            // Validating if payer in group
            if (!group.getUsers().contains(user)) {
                throw new RuntimeException("User is not a member of the group!");
            }
        }
        BigDecimal totalPaidAmount = expenseRequestDTO.getPayers().stream()
                .map(PayerDTO::getPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        Expense expense = expenseService.createExpense(expenseRequestDTO.getDescription(), totalPaidAmount, group, new ArrayList<>());

        for (PayerDTO payer : expenseRequestDTO.getPayers()) {
            User user = userService.getUserById(payer.getUserId());
            saveExpenseParticipant(expense, user, payer.getPaidAmount(), BigDecimal.ZERO);
            updateUserGroupBalance(group, user, payer.getPaidAmount(), BigDecimal.ZERO);
        }
        // Find total amount
        // Create expense
        // Writing to database at the last for consistency
        // processing based on splitType
        switch(expenseRequestDTO.getSplitType()) {
            case "equal":
                processEqualSplit(expense, expenseRequestDTO.getParticipants());
                break;
            case "shares":
                processSharesSplit(expense, expenseRequestDTO.getParticipants());
                break;
            case "percentage":
                processPercentageSplit(expense, expenseRequestDTO.getParticipants());
                break;
            case "unequal":
                processUnequalSplit(expense, expenseRequestDTO.getParticipants());
                break;
            default:
                throw new IllegalArgumentException("Invalid split type!");
        }

        return new ExpenseDTO(expense);
    }

    private void processEqualSplit(Expense expense, List<ParticipantDTO> participants) {
        BigDecimal shareAmount = expense.getTotalAmount().divide(BigDecimal.valueOf(participants.size()), 2, RoundingMode.HALF_UP);
        for (ParticipantDTO participant : participants) {
            User user = userService.getUserById(participant.getUserId());
            saveExpenseParticipant(expense, user, BigDecimal.ZERO, shareAmount);
            updateUserGroupBalance(expense.getGroup(), user, BigDecimal.ZERO, shareAmount);
        }
    }

    private void processSharesSplit(Expense expense, List<ParticipantDTO> participants) {
        int totalShares = participants.stream().mapToInt(ParticipantDTO::getShares).sum();
        for (ParticipantDTO participant : participants) {
            BigDecimal shareAmount = expense.getTotalAmount().multiply(BigDecimal.valueOf(participant.getShares() / (double) totalShares)).setScale(2, RoundingMode.HALF_UP);
            User user = userService.getUserById(participant.getUserId());
            saveExpenseParticipant(expense, user, BigDecimal.ZERO, shareAmount);
            updateUserGroupBalance(expense.getGroup(), user, BigDecimal.ZERO, shareAmount);
        }
    }

    private void processPercentageSplit(Expense expense, List<ParticipantDTO> participants) {
        double totalPercentage = participants.stream().mapToDouble(ParticipantDTO::getPercentage).sum();
        if (totalPercentage != 100) {
            throw new IllegalArgumentException("Percentages must sum up to 100%!");
        }
        for (ParticipantDTO participant : participants) {
            BigDecimal shareAmount = expense.getTotalAmount().multiply(BigDecimal.valueOf(participant.getPercentage() / 100.0)).setScale(2, RoundingMode.HALF_UP);
            User user = userService.getUserById(participant.getUserId());
            saveExpenseParticipant(expense, user, BigDecimal.ZERO, shareAmount);
            updateUserGroupBalance(expense.getGroup(), user, BigDecimal.ZERO, shareAmount);
        }
    }

    private void processUnequalSplit(Expense expense, List<ParticipantDTO> participants) {
        BigDecimal totalShareAmount = participants.stream().map(ParticipantDTO::getShareAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalShareAmount.compareTo(expense.getTotalAmount()) != 0) {
            throw new IllegalArgumentException("Total share amount must match total paid amount!");
        }
        for (ParticipantDTO participant : participants) {
            User user = userService.getUserById(participant.getUserId());
            saveExpenseParticipant(expense, user, BigDecimal.ZERO, participant.getShareAmount());
            updateUserGroupBalance(expense.getGroup(), user, BigDecimal.ZERO, participant.getShareAmount());
        }
    }

    private void saveExpenseParticipant(Expense expense, User user, BigDecimal paidAmount, BigDecimal shareAmount) {
        ExpenseParticipant participant = expenseParticipantService.getOrNewParticipant(expense, user);
        if (paidAmount.compareTo(BigDecimal.ZERO) != 0) {
            participant.setPaidAmount(paidAmount);
        }
        if (shareAmount.compareTo(BigDecimal.ZERO) != 0) {
            participant.setShareAmount(shareAmount);
        }
        expense.addParticipant(participant);
        participant = expenseParticipantService.saveExpenseParticipant(participant);
        expense = expenseService.saveExpense(expense);
    }

    private void updateUserGroupBalance(Group group, User user, BigDecimal paidAmount, BigDecimal shareAmount) {
        UserGroupBalance balance = userGroupBalanceService.getOrNewBalance(group, user);
        balance.updateBalance(paidAmount, shareAmount);
        balance = userGroupBalanceService.saveUserGroupBalance(balance);
    }
}