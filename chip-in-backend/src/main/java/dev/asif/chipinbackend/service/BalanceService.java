package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.SettlementTransactionDTO;
import dev.asif.chipinbackend.dto.UserGroupBalanceDTO;

import java.util.List;

public interface BalanceService {
    List<UserGroupBalanceDTO> getAllBalancesInGroup(Long groupId);
    List<SettlementTransactionDTO> getAllSettlementTransactions(Long groupId);
}
