package dev.asif.chipinbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "user_payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payer_id", nullable = false)
    private User payer;

    @ManyToOne
    @JoinColumn(name = "payee_id", nullable = false)
    private User payee;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    public void onCreate() {
        this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
}
