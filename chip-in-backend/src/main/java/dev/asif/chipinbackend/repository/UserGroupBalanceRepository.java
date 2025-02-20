package dev.asif.chipinbackend.repository;

import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserGroupBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGroupBalanceRepository extends JpaRepository<UserGroupBalance, Long> {
    Optional<UserGroupBalance> findByUserAndGroup(User user, Group group);
    List<UserGroupBalance> findByGroup(Group group);
}