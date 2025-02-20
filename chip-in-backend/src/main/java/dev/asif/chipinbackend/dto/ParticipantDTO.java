package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.model.ExpenseParticipant;
import lombok.Data;

@Data
public class ParticipantDTO {
    private Long userId;
    private Double percentage;
    private Integer shares;
    private Double shareAmount;
}
