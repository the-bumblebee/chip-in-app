package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.GroupBalanceResponseDTO;
import dev.asif.chipinbackend.dto.SettlementTransactionResponseDTO;
import dev.asif.chipinbackend.service.GroupBalanceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/balance")
public class BalanceController {

    GroupBalanceManager balanceService;

    @Autowired
    public BalanceController(GroupBalanceManager balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping
    public List<GroupBalanceResponseDTO> getAllBalances(@PathVariable Long groupId) {
        return balanceService.getAllBalancesInGroup(groupId);
    }

    @GetMapping("/transactions")
    public List<SettlementTransactionResponseDTO> getAllSettlementTransactions(@PathVariable Long groupId) {
        return balanceService.getAllSettlementTransactions(groupId);
    }
}
