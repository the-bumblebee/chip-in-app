package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.SettlementTransactionResponseDTO;

import java.util.List;

public interface SettlementManager {
    List<SettlementTransactionResponseDTO> getAllSettlementTransactions(Long groupId);
}
