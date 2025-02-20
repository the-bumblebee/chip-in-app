package dev.asif.chipinbackend.repository;

import dev.asif.chipinbackend.model.Expense;
import dev.asif.chipinbackend.model.ExpenseParticipant;
import dev.asif.chipinbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseParticipantRepository extends JpaRepository<ExpenseParticipant, Long> {
    Optional<ExpenseParticipant> findByExpenseAndUser(Expense expense, User user);
}
