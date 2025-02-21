package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.SettlementTransactionDTO;
import dev.asif.chipinbackend.dto.core.UserGroupBalanceDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserGroupBalance;

import java.math.BigDecimal;
import java.util.List;

public interface BalanceService {
    List<UserGroupBalanceDTO> getAllBalancesInGroup(Group group);
    List<SettlementTransactionDTO> getAllSettlementTransactions(Group group);
    UserGroupBalance getBalanceByGroupAndUser(Group group, User user);
    UserGroupBalance getOrCreateBalance(Group group, User user);
    void updateBalanceByGroupAndUser(Group group, User user, BigDecimal paidAmount);
    UserGroupBalance saveBalance(UserGroupBalance balance);
}
