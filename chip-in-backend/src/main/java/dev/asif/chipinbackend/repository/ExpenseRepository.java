package dev.asif.chipinbackend.repository;

import dev.asif.chipinbackend.model.Expense;
import dev.asif.chipinbackend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByGroup(Group group);
}
