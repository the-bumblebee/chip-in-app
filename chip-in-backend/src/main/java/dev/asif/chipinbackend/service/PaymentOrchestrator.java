package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.PaymentRequestDTO;
import dev.asif.chipinbackend.dto.PaymentResponseDTO;
import dev.asif.chipinbackend.dto.SettlementTransactionResponseDTO;

import java.util.List;

public interface PaymentOrchestrator {
    PaymentResponseDTO createPayment(Long groupId, PaymentRequestDTO paymentRequestDTO);
    List<SettlementTransactionResponseDTO> getAllSettlementTransactions(Long groupId);
}
