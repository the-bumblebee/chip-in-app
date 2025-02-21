package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.SettlementTransactionDTO;
import dev.asif.chipinbackend.dto.core.UserGroupBalanceDTO;
import dev.asif.chipinbackend.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/balance")
public class BalanceController {

    BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping
    public List<UserGroupBalanceDTO> getAllBalances(@PathVariable Long groupId) {
        return balanceService.getAllBalancesInGroup(groupId);
    }

    @GetMapping("/transactions")
    public List<SettlementTransactionDTO> getAllSettlementTransactions(@PathVariable Long groupId) {
        return balanceService.getAllSettlementTransactions(groupId);
    }
}
