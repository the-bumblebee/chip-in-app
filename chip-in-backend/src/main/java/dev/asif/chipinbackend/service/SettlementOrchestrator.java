package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.SettlementTransactionResponseDTO;

import java.util.List;

public interface SettlementOrchestrator {
    List<SettlementTransactionResponseDTO> getAllSettlementTransactions(Long groupId);
}
