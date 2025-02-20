package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    ResponseEntity<User> createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
}
