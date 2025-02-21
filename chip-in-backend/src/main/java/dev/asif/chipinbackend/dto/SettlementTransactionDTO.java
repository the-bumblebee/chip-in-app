package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.dto.core.UserDTO;
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
