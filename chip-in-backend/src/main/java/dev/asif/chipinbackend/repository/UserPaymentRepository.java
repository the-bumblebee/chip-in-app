package dev.asif.chipinbackend.repository;

import dev.asif.chipinbackend.model.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
}
