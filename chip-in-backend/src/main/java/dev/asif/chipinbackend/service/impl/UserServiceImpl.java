package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.exception.ResourceNotFoundException;
import dev.asif.chipinbackend.exception.UserAlreadyExistsException;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.repository.UserRepository;
import dev.asif.chipinbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists!");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userRepository.save(user));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " does not exist!"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map((user) -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User with id " + id + " not found!"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
