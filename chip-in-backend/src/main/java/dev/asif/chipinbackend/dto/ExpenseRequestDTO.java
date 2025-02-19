package dev.asif.chipinbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExpenseRequestDTO {
    private String description;
    private String splitType;
    private List<PayerDTO> payers;
    private List<ParticipantDTO> participants;
}
