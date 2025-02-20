package dev.asif.chipinbackend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayerDTO {
    private Long userId;
    private BigDecimal paidAmount;
}
