package dev.asif.chipinbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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

    @Column(name = "total_paid_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPaidAmount = BigDecimal.ZERO;

    @Column(name = "total_share_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalShareAmount = BigDecimal.ZERO;

    public UserGroupBalance(User user, Group group, BigDecimal totalPaidAmount, BigDecimal totalShareAmount) {
        this.user = user;
        this.group = group;
        this.totalPaidAmount = totalPaidAmount;
        this.totalShareAmount = totalShareAmount;
    }

    public BigDecimal getNetBalance() {
        return this.totalShareAmount.subtract(this.totalPaidAmount);
    }

    public void updateBalance(BigDecimal paidAmount, BigDecimal shareAmount) {
        this.totalPaidAmount = this.totalPaidAmount.add(paidAmount);
        this.totalShareAmount = this.totalShareAmount.add(shareAmount);
    }
}
