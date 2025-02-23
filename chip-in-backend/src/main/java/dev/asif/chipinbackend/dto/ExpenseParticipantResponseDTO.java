package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.model.ExpenseParticipant;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseParticipantResponseDTO {
    private UserDTO user;
    private BigDecimal paidAmount;
    private BigDecimal shareAmount;

    public ExpenseParticipantResponseDTO(ExpenseParticipant participant) {
        this.user = new UserDTO(participant.getUser());
        this.paidAmount = participant.getPaidAmount();
        this.shareAmount = participant.getShareAmount();
    }
}
