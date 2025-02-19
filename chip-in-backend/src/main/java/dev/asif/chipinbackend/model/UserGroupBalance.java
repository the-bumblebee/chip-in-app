package dev.asif.chipinbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_group_balances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGroupBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_paid_amount")
    private double totalPaidAmount = 0.0;

    @Column(name = "total_share_amount")
    private double totalShareAmount = 0.0;

    public double getNetBalance() {
        return this.totalShareAmount - this.totalPaidAmount;
    }

    public void updateBalance(double paidAmount, double shareAmount) {
        this.totalPaidAmount += paidAmount;
        this.totalShareAmount += shareAmount;
    }
}
