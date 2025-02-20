package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.model.ExpenseParticipant;
import lombok.Data;

@Data
public class ParticipantDTO {
    private Long userId;
    private double percentage;
    private int shares;
    private double shareAmount;
}
