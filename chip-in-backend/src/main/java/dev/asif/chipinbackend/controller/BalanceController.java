package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.GroupBalanceResponseDTO;
import dev.asif.chipinbackend.dto.SettlementTransactionResponseDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.service.SettlementOrchestrator;
import dev.asif.chipinbackend.service.core.GroupService;
import dev.asif.chipinbackend.service.core.UserGroupBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/balance")
public class BalanceController {

    GroupService groupService;
    UserGroupBalanceService userGroupBalanceService;
    SettlementOrchestrator settlementOrchestrator;

    @Autowired
    public BalanceController(GroupService groupService, UserGroupBalanceService userGroupBalanceService, SettlementOrchestrator settlementOrchestrator) {
        this.userGroupBalanceService = userGroupBalanceService;
        this.groupService = groupService;
        this.settlementOrchestrator = settlementOrchestrator;
    }

    @GetMapping
    public List<GroupBalanceResponseDTO> getAllBalances(@PathVariable Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return userGroupBalanceService.getBalancesByGroup(group).stream()
                .map(GroupBalanceResponseDTO::new)
                .toList();
    }

    @GetMapping("/transactions")
    public List<SettlementTransactionResponseDTO> getAllSettlementTransactions(@PathVariable Long groupId) {
        return settlementOrchestrator.getAllSettlementTransactions(groupId);
    }
}
