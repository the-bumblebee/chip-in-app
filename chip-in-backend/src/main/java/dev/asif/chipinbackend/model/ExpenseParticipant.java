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
    private double paidAmount = 0.0;

    @Column(name = "share_amount")
    private double shareAmount = 0.0;

    public ExpenseParticipant(Expense expense, User user, double paidAmount, double shareAmount) {
        this.expense = expense;
        this.user = user;
        this.paidAmount = paidAmount;
        this.shareAmount = shareAmount;
    }
}
