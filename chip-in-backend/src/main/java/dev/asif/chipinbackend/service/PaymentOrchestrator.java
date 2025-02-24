package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.PaymentRequestDTO;
import dev.asif.chipinbackend.dto.PaymentResponseDTO;
import dev.asif.chipinbackend.dto.SettlementTransactionResponseDTO;

import java.util.List;

public interface PaymentOrchestrator {
    List<PaymentResponseDTO> getAllPaymentsInGroup(Long groupId);
    PaymentResponseDTO createPayment(Long groupId, PaymentRequestDTO paymentRequestDTO);
    PaymentResponseDTO updatePayment(Long groupId, Long paymentId, PaymentRequestDTO paymentRequestDTO);
    void deletePayment(Long groupId, Long paymentId);
    List<SettlementTransactionResponseDTO> getAllSettlementTransactions(Long groupId);
}
