package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.PaymentRequestDTO;
import dev.asif.chipinbackend.dto.PaymentResponseDTO;
import dev.asif.chipinbackend.dto.SettlementTransactionResponseDTO;
import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserGroupBalance;
import dev.asif.chipinbackend.model.UserPayment;
import dev.asif.chipinbackend.service.PaymentOrchestrator;
import dev.asif.chipinbackend.service.core.GroupService;
import dev.asif.chipinbackend.service.core.UserGroupBalanceService;
import dev.asif.chipinbackend.service.core.UserPaymentService;
import dev.asif.chipinbackend.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class PaymentOrchestratorImpl implements PaymentOrchestrator {

    private final UserService userService;
    private final GroupService groupService;
    private final UserPaymentService userPaymentService;
    private final UserGroupBalanceService userGroupBalanceService;

    @Autowired
    public PaymentOrchestratorImpl(
            UserService userService,
            GroupService groupService,
            UserPaymentService userPaymentService,
            UserGroupBalanceService userGroupBalanceService){
        this.userService = userService;
        this.groupService = groupService;
        this.userPaymentService = userPaymentService;
        this.userGroupBalanceService = userGroupBalanceService;
    }

    @Override
    public List<PaymentResponseDTO> getAllPaymentsInGroup(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return userPaymentService.getAllUserPaymentsByGroup(group).stream()
                .map(PaymentResponseDTO::new)
                .toList();
    }

    @Override
    public PaymentResponseDTO createPayment(Long groupId, PaymentRequestDTO paymentRequestDTO) {
        Group group = groupService.getGroupById(groupId);
        User payer = userService.getUserById(paymentRequestDTO.getPayerId());
        User payee = userService.getUserById(paymentRequestDTO.getPayeeId());
        if (!group.getUsers().contains(payer) || !group.getUsers().contains(payee)) {
            throw new IllegalArgumentException("User is not a member of the group!");
        }
        UserGroupBalance payerGroupBalance = userGroupBalanceService.getBalanceByGroupAndUser(group, payer);
        UserGroupBalance payeeGroupBalance = userGroupBalanceService.getBalanceByGroupAndUser(group, payee);
        payerGroupBalance.updateBalance(paymentRequestDTO.getAmount(), BigDecimal.ZERO);
        payeeGroupBalance.updateBalance(paymentRequestDTO.getAmount().negate(), BigDecimal.ZERO);
        userGroupBalanceService.saveUserGroupBalance(payerGroupBalance);
        userGroupBalanceService.saveUserGroupBalance(payeeGroupBalance);
        return new PaymentResponseDTO(userPaymentService.createUserPayment(payer, payee, group, paymentRequestDTO.getAmount()));
    }

    @Override
    public PaymentResponseDTO updatePayment(Long groupId, Long paymentId, PaymentRequestDTO paymentRequestDTO) {
        Group group = groupService.getGroupById(groupId);
        UserPayment userPayment = userPaymentService.getUserPaymentById(paymentId);
        if (!userPayment.getPayer().getId().equals(paymentRequestDTO.getPayerId()) || !userPayment.getPayee().getId().equals(paymentRequestDTO.getPayeeId())) {
            throw new RuntimeException("Users cannot be updated for a payment!");
        }
        UserGroupBalance payerGroupBalance = userGroupBalanceService.getBalanceByGroupAndUser(group, userPayment.getPayer());
        UserGroupBalance payeeGroupBalance = userGroupBalanceService.getBalanceByGroupAndUser(group, userPayment.getPayee());
        BigDecimal adjustedAmount = paymentRequestDTO.getAmount().add(userPayment.getAmount().negate());
        payerGroupBalance.updateBalance(adjustedAmount, BigDecimal.ZERO);
        payeeGroupBalance.updateBalance(adjustedAmount.negate(), BigDecimal.ZERO);
        userGroupBalanceService.saveUserGroupBalance(payerGroupBalance);
        userGroupBalanceService.saveUserGroupBalance(payeeGroupBalance);
        userPayment.setAmount(paymentRequestDTO.getAmount());
        return new PaymentResponseDTO(userPaymentService.saveUserPayment(userPayment));
    }

    @Override
    public void deletePayment(Long groupId, Long paymentId) {
        Group group = groupService.getGroupById(groupId);
        UserPayment userPayment = userPaymentService.getUserPaymentById(paymentId);
        if (!group.getPayments().contains(userPayment)) {
            throw new IllegalArgumentException("The group does not contain the specified payment!");
        }
        group.removePayment(userPayment);
        groupService.saveGroup(group);
        userPaymentService.deleteUserPayment(paymentId);
    }

    @Override
    public List<SettlementTransactionResponseDTO> getAllSettlementTransactions(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        List<UserGroupBalance> balances = userGroupBalanceService.getBalancesByGroup(group).stream()
                .sorted((balance1, balance2) -> balance1.getNetBalance().compareTo(balance2.getNetBalance()))
                .toList();

        int l = 0, r = balances.size() - 1;
        BigDecimal receivable = (l <= r) ? balances.get(l).getNetBalance() : BigDecimal.ZERO;
        BigDecimal payable = (l <= r) ? balances.get(r).getNetBalance() : BigDecimal.ZERO;
        List<SettlementTransactionResponseDTO> transactions = new ArrayList<>();
        while (l < r && !balances.get(l).getNetBalance().equals(BigDecimal.ZERO)) {
            if (receivable.add(payable).compareTo(BigDecimal.ZERO) > 0) {
                payable = payable.add(receivable);
                transactions.add(
                        new SettlementTransactionResponseDTO(
                                new UserDTO(balances.get(r).getUser()),
                                new UserDTO(balances.get(l).getUser()),
                                receivable.negate())
                );
                l += 1;
                receivable = balances.get(l).getNetBalance();
            }
            else if (receivable.add(payable).compareTo(BigDecimal.ZERO) < 0) {
                receivable = receivable.add(payable);
                transactions.add(
                        new SettlementTransactionResponseDTO(
                                new UserDTO(balances.get(r).getUser()),
                                new UserDTO(balances.get(l).getUser()),
                                payable)
                );
                r -= 1;
                payable = balances.get(r).getNetBalance();
            }
            else {
                transactions.add(
                        new SettlementTransactionResponseDTO(
                                new UserDTO(balances.get(r).getUser()),
                                new UserDTO(balances.get(l).getUser()),
                                payable)
                );
                l += 1;
                r -= 1;
                receivable = balances.get(l).getNetBalance();
                payable = balances.get(r).getNetBalance();
            }
        }

        return transactions;
    }
}
