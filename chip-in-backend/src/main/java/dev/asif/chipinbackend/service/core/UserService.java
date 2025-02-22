package dev.asif.chipinbackend.service.core;

import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);

    List<User> getAllUsers();
    List<User> getUsersByGroup(Group group);
    User saveUser(User user);
}
