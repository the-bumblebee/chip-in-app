package dev.asif.chipinbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettlementTransactionDTO {
    private UserDTO payer;
    private UserDTO payee;
    private double amount;
}
