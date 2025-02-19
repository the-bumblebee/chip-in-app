package dev.asif.chipinbackend.repository;

import dev.asif.chipinbackend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
