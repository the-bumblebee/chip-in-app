package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.SettlementTransactionDTO;
import dev.asif.chipinbackend.dto.UserDTO;
import dev.asif.chipinbackend.dto.UserGroupBalanceDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.UserGroupBalance;
import dev.asif.chipinbackend.repository.GroupRepository;
import dev.asif.chipinbackend.repository.UserGroupBalanceRepository;
import dev.asif.chipinbackend.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BalanceServiceImpl implements BalanceService {

    GroupRepository groupRepository;
    UserGroupBalanceRepository userGroupBalanceRepository;

    @Autowired
    public BalanceServiceImpl(GroupRepository groupRepository, UserGroupBalanceRepository userGroupBalanceRepository){
        this.groupRepository = groupRepository;
        this.userGroupBalanceRepository = userGroupBalanceRepository;
    }

    @Override
    public List<UserGroupBalanceDTO> getAllBalancesInGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group with id " + groupId + " not found!"));
        return userGroupBalanceRepository.findByGroup(group).stream()
                .map(UserGroupBalanceDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<SettlementTransactionDTO> getAllSettlementTransactions(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group with id " + groupId + " not found!"));
        List<UserGroupBalance> balances = userGroupBalanceRepository.findByGroup(group).stream()
                .sorted((balance1, balance2) -> {
                    if (balance1.getNetBalance() - balance2.getNetBalance() < 0) {
                        return -1;
                    }
                    else if (balance1.getNetBalance() - balance2.getNetBalance() > 0) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }).toList();

        int l = 0, r = balances.size() - 1;
        double receivable = balances.get(l).getNetBalance();
        double payable = balances.get(r).getNetBalance();
        List<SettlementTransactionDTO> transactions = new ArrayList<>();
        while (l < r) {
            if (receivable + payable > 0) {
                payable += receivable;
                transactions.add(
                        new SettlementTransactionDTO(
                                new UserDTO(balances.get(r).getUser()),
                                new UserDTO(balances.get(l).getUser()),
                                - receivable)
                );
                l += 1;
                receivable = balances.get(l).getNetBalance();
            }
            else if (receivable + payable < 0) {
                receivable += payable;
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
}
