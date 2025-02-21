package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.dto.core.ExpenseParticipantDTO;
import dev.asif.chipinbackend.model.Expense;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ExpenseResponseDTO {
    private Long id;
    private String description;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

    public ExpenseResponseDTO(Expense expense) {
        this.id = expense.getId();
        this.description = expense.getDescription();
        this.totalAmount = expense.getTotalAmount();
        this.createdAt = expense.getCreatedAt();
    }
}
