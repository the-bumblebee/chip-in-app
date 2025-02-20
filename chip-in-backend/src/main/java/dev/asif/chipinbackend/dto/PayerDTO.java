package dev.asif.chipinbackend.dto;

import lombok.Data;

@Data
public class PayerDTO {
    private Long userId;
    private double paidAmount;
}
