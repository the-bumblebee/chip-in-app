package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.model.ExpenseParticipant;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseParticipantDTO {
    private Long userId;
    private BigDecimal paidAmount;
    private BigDecimal shareAmount;

    public ExpenseParticipantDTO(ExpenseParticipant participant) {
        this.userId = participant.getUser().getId();
        this.paidAmount = participant.getPaidAmount();
        this.shareAmount = participant.getShareAmount();
    }
}
