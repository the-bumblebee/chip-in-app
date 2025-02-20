package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.SettlementTransactionDTO;
import dev.asif.chipinbackend.dto.UserGroupBalanceDTO;
import dev.asif.chipinbackend.model.UserGroupBalance;

import java.math.BigDecimal;
import java.util.List;

public interface BalanceService {
    List<UserGroupBalanceDTO> getAllBalancesInGroup(Long groupId);
    List<SettlementTransactionDTO> getAllSettlementTransactions(Long groupId);
    UserGroupBalance getBalanceByGroupIdAndUserId(Long groupId, Long userId);
    void updateBalanceByGroupIdAndUserId(Long userId, Long groupId, BigDecimal paidAmount);
}
