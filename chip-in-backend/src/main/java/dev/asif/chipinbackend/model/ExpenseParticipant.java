package dev.asif.chipinbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expense_participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "paid_amount")
    private double paidAmount;

    @Column(name = "share_ammount")
    private double shareAmount;
}
