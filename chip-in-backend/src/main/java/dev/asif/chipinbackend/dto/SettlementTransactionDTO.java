package dev.asif.chipinbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SettlementTransactionDTO {
    private UserDTO payer;
    private UserDTO payee;
    private BigDecimal amount;
}
