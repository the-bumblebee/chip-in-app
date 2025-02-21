package dev.asif.chipinbackend.dto.core;

import dev.asif.chipinbackend.model.Expense;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ExpenseDTO {
    private Long id;
    private Long groupId;
    private String description;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private Set<ExpenseParticipantDTO> participants;

    public ExpenseDTO(Expense expense) {
        this.id = expense.getId();
        this.groupId = expense.getGroup().getId();
        this.description = expense.getDescription();
        this.totalAmount = expense.getTotalAmount();
        this.createdAt = expense.getCreatedAt();
        this.participants = expense.getParticipants().stream()
                .map(ExpenseParticipantDTO::new)
                .collect(Collectors.toSet());
    }
}
