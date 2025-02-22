package dev.asif.chipinbackend.repository;

import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
