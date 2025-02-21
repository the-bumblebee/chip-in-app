package dev.asif.chipinbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @OneToMany(mappedBy = "expense")
    private List<ExpenseParticipant> participants = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public void addParticipant(ExpenseParticipant participant) {
        this.participants.add(participant);
        participant.setExpense(this);
    }

    public void removeParticipant(ExpenseParticipant participant) {
        this.participants.remove(participant);
        participant.setExpense(this);
    }
}
