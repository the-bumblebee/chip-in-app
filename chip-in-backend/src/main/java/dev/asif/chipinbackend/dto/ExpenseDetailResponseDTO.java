package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.model.Expense;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExpenseDetailResponseDTO {
    private Long id;
    private String description;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    List<ExpenseParticipantResponseDTO> participants;

    public ExpenseDetailResponseDTO(Expense expense) {
        this.id = expense.getId();
        this.description = expense.getDescription();
        this.totalAmount = expense.getTotalAmount();
        this.createdAt = expense.getCreatedAt();
        this.participants = expense.getParticipants().stream()
                .map(ExpenseParticipantResponseDTO::new)
                .toList();
    }
}
