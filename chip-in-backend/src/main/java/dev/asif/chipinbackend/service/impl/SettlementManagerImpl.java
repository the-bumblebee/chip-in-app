package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.SettlementTransactionResponseDTO;
import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.UserGroupBalance;
import dev.asif.chipinbackend.service.SettlementManager;
import dev.asif.chipinbackend.service.core.GroupService;
import dev.asif.chipinbackend.service.core.UserGroupBalanceService;
import dev.asif.chipinbackend.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SettlementManagerImpl implements SettlementManager {

    private final GroupService groupService;
    private final UserGroupBalanceService userGroupBalanceService;

    @Autowired
    public SettlementManagerImpl(GroupService groupService, UserGroupBalanceService userGroupBalanceService){
        this.groupService = groupService;
        this.userGroupBalanceService = userGroupBalanceService;
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
        while (l < r) {
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
