package dev.asif.chipinbackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "groups")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPayment> payments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getGroups().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getGroups().remove(this);
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
        expense.setGroup(this);
    }

    public void removeExpense(Expense expense) {
        this.expenses.remove(expense);
        expense.setGroup(null);
    }

    public void addPayment(UserPayment payment) {
        this.payments.add(payment);
        payment.setGroup(this);
    }

    public void removePayment(UserPayment payment) {
        this.payments.remove(payment);
        payment.setGroup(null);
    }
}
