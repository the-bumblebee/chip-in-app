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
        Double totalPaidAmount = request.getPayers().stream()
                .mapToDouble(PayerDTO::getPaidAmount).sum();

        Expense expense = new Expense();
        expense.setGroup(group);
        expense.setDescription(request.getDescription());
        expense.setTotalAmount(totalPaidAmount);

        expenseRepository.save(expense);

        for (PayerDTO payer : request.getPayers()) {
            User user = userRepository.findById(payer.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + payer.getUserId() + " not found!"));
            saveExpenseParticipant(expense, user, payer.getPaidAmount(), 0.0);
            updateUserGroupBalance(user, group, payer.getPaidAmount(), 0.0);
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
        double shareAmount = expense.getTotalAmount() / (double) participants.size();
        for (ParticipantDTO participant : participants) {
            User user = userRepository.findById(participant.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + participant.getUserId() + " not found!"));
            saveExpenseParticipant(expense, user, 0.0, shareAmount);
            updateUserGroupBalance(user, expense.getGroup(), 0.0, shareAmount);
        }
    }

    private void processSharesSplit(Expense expense, List<ParticipantDTO> participants) {
        int totalShares = participants.stream().mapToInt(ParticipantDTO::getShares).sum();
        for (ParticipantDTO participant : participants) {
            double shareAmount = (participant.getShares() / (double) totalShares) * expense.getTotalAmount();
            User user = userRepository.findById(participant.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + participant.getUserId() + " not found!"));
            saveExpenseParticipant(expense, user, 0.0, shareAmount);
            updateUserGroupBalance(user, expense.getGroup(), 0.0, shareAmount);
        }
    }

    private void processPercentageSplit(Expense expense, List<ParticipantDTO> participants) {
        double totalPercentage = participants.stream().mapToDouble(ParticipantDTO::getPercentage).sum();
        if (totalPercentage != 100) {
            throw new IllegalArgumentException("Percentages must sum up to 100%!");
        }
        for (ParticipantDTO participant : participants) {
            double shareAmount = (participant.getPercentage() / 100.0) * expense.getTotalAmount();
            User user = userRepository.findById(participant.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + participant.getUserId() + " not found!"));
            saveExpenseParticipant(expense, user, 0.0, shareAmount);
            updateUserGroupBalance(user, expense.getGroup(), 0.0, shareAmount);
        }
    }

    private void processUnequalSplit(Expense expense, List<ParticipantDTO> participants) {
        double totalShareAmount = participants.stream().mapToDouble(ParticipantDTO::getShareAmount).sum();
        if (totalShareAmount != expense.getTotalAmount()) {
            throw new IllegalArgumentException("Total share amount must match total paid amount!");
        }
        for (ParticipantDTO participant : participants) {
            User user = userRepository.findById(participant.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + participant.getUserId() + " not found!"));
            saveExpenseParticipant(expense, user, 0.0, participant.getShareAmount());
            updateUserGroupBalance(user, expense.getGroup(), 0.0, participant.getShareAmount());
        }
    }

    private void saveExpenseParticipant(Expense expense, User user, double paidAmount, double shareAmount) {
        ExpenseParticipant participant = expenseParticipantRepository.findByExpenseAndUser(expense, user)
                .orElse(new ExpenseParticipant(expense, user, 0.0, 0.0));
        if (paidAmount != 0.0) {
            participant.setPaidAmount(paidAmount);
        }
        if (shareAmount != 0.0) {
            participant.setShareAmount(shareAmount);
        }
        expense.addParticipant(participant);
        expenseParticipantRepository.save(participant);
    }

    private void updateUserGroupBalance(User user, Group group, double paidAmount, double shareAmount) {
        UserGroupBalance balance = userGroupBalanceRepository.findByUserAndGroup(user, group)
                .orElse(new UserGroupBalance(user, group, 0.0, 0.0));
        balance.updateBalance(paidAmount, shareAmount);
        userGroupBalanceRepository.save(balance);
    }
}
