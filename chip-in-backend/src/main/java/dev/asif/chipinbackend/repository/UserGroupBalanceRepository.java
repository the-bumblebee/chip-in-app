package dev.asif.chipinbackend.repository;

import dev.asif.chipinbackend.model.UserGroupBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupBalanceRepository extends JpaRepository<UserGroupBalance, Long> {
}