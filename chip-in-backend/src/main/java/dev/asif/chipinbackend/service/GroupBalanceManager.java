package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.GroupBalanceResponseDTO;
import dev.asif.chipinbackend.dto.SettlementTransactionResponseDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserGroupBalance;

import java.math.BigDecimal;
import java.util.List;

public interface GroupBalanceManager {
    List<GroupBalanceResponseDTO> getAllBalancesInGroup(Long groupId);
    List<SettlementTransactionResponseDTO> getAllSettlementTransactions(Long groupId);
    UserGroupBalance getBalanceByGroupAndUser(Group group, User user);
    UserGroupBalance getOrCreateBalance(Group group, User user);
    void updateBalanceByGroupAndUser(Group group, User user, BigDecimal paidAmount);
    UserGroupBalance saveBalance(UserGroupBalance balance);
}
