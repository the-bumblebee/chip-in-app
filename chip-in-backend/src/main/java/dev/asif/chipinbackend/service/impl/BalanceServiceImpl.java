package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.SettlementTransactionDTO;
import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.dto.core.UserGroupBalanceDTO;
import dev.asif.chipinbackend.exception.ResourceNotFoundException;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserGroupBalance;
import dev.asif.chipinbackend.repository.UserGroupBalanceRepository;
import dev.asif.chipinbackend.service.BalanceService;
import dev.asif.chipinbackend.service.core.GroupService;
import dev.asif.chipinbackend.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final UserService userService;
    private final GroupService groupService;
    private final UserGroupBalanceRepository userGroupBalanceRepository;

    @Autowired
    public BalanceServiceImpl(UserService userService, GroupService groupService, UserGroupBalanceRepository userGroupBalanceRepository){
        this.userService = userService;
        this.groupService = groupService;
        this.userGroupBalanceRepository = userGroupBalanceRepository;
    }

    @Override
    public List<UserGroupBalanceDTO> getAllBalancesInGroup(Group group) {
        return userGroupBalanceRepository.findByGroup(group).stream()
                .map(UserGroupBalanceDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<SettlementTransactionDTO> getAllSettlementTransactions(Group group) {
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
    public UserGroupBalance getBalanceByGroupAndUser(Group group, User user) {
        return userGroupBalanceRepository.findByGroupAndUser(group, user)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find balance for the user in the group!"));
    }

    @Override
    public UserGroupBalance getOrCreateBalance(Group group, User user) {
        return userGroupBalanceRepository.findByGroupAndUser(group, user)
                .orElse(new UserGroupBalance(user, group, BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @Override
    public void updateBalanceByGroupAndUser(Group group, User user, BigDecimal paidAmount) {
        UserGroupBalance balance = getBalanceByGroupAndUser(group, user);
        balance.updateBalance(paidAmount, BigDecimal.ZERO);
    }

    @Override
    public UserGroupBalance saveBalance(UserGroupBalance balance) {
        return userGroupBalanceRepository.save(balance);
    }
}
