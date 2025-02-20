package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.SettlementTransactionDTO;
import dev.asif.chipinbackend.dto.UserDTO;
import dev.asif.chipinbackend.dto.UserGroupBalanceDTO;
import dev.asif.chipinbackend.exception.ResourceNotFoundException;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserGroupBalance;
import dev.asif.chipinbackend.repository.UserGroupBalanceRepository;
import dev.asif.chipinbackend.service.BalanceService;
import dev.asif.chipinbackend.service.GroupService;
import dev.asif.chipinbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BalanceServiceImpl implements BalanceService {

    UserService userService;
    GroupService groupService;
    UserGroupBalanceRepository userGroupBalanceRepository;

    @Autowired
    public BalanceServiceImpl(UserService userService, GroupService groupService, UserGroupBalanceRepository userGroupBalanceRepository){
        this.userService = userService;
        this.groupService = groupService;
        this.userGroupBalanceRepository = userGroupBalanceRepository;
    }

    @Override
    public List<UserGroupBalanceDTO> getAllBalancesInGroup(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return userGroupBalanceRepository.findByGroup(group).stream()
                .map(UserGroupBalanceDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<SettlementTransactionDTO> getAllSettlementTransactions(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        List<UserGroupBalance> balances = userGroupBalanceRepository.findByGroup(group).stream()
                .sorted((balance1, balance2) -> balance1.getNetBalance().compareTo(balance2.getNetBalance()))
                .toList();

        int l = 0, r = balances.size() - 1;
        BigDecimal receivable = (l <= r) ? balances.get(l).getNetBalance() : BigDecimal.ZERO;
        BigDecimal payable = (l <= r) ? balances.get(r).getNetBalance() : BigDecimal.ZERO;
        List<SettlementTransactionDTO> transactions = new ArrayList<>();
        while (l < r) {
            if (receivable.add(payable).compareTo(BigDecimal.ZERO) > 0) {
                payable = payable.add(receivable);
                transactions.add(
                        new SettlementTransactionDTO(
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
                        new SettlementTransactionDTO(
                                new UserDTO(balances.get(r).getUser()),
                                new UserDTO(balances.get(l).getUser()),
                                payable)
                );
                r -= 1;
                payable = balances.get(r).getNetBalance();
            }
            else {
                transactions.add(
                        new SettlementTransactionDTO(
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

    @Override
    public UserGroupBalance getBalanceByGroupIdAndUserId(Long groupId, Long userId) {
        Group group = groupService.getGroupById(groupId);
        User user = userService.getUserById(userId);

        return userGroupBalanceRepository.findByGroupAndUser(group, user)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find balance for the user in the group!"));
    }

    @Override
    public void updateBalanceByGroupIdAndUserId(Long groupId, Long userId, BigDecimal paidAmount) {
        UserGroupBalance balance = getBalanceByGroupIdAndUserId(groupId, userId);
        balance.updateBalance(paidAmount, BigDecimal.ZERO);
    }
}
