package dev.asif.chipinbackend.repository;

import dev.asif.chipinbackend.model.ExpenseParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseParticipantRepository extends JpaRepository<ExpenseParticipant, Long> {
}
