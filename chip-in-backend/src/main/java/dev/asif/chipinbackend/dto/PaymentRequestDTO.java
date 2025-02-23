package dev.asif.chipinbackend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
    private Long payerId;
    private Long payeeId;
    private BigDecimal amount;
}
