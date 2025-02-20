package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.model.ExpenseParticipant;
import lombok.Data;

@Data
public class ExpenseParticipantDTO {
    private Long userId;
    private double paidAmount;
    private double shareAmount;

    public ExpenseParticipantDTO(ExpenseParticipant participant) {
        this.userId = participant.getUser().getId();
        this.paidAmount = participant.getPaidAmount();
        this.shareAmount = participant.getShareAmount();
    }
}
