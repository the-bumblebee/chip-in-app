package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.UserResponseDTO;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDTO> getUsers() {
        return userService.getAllUsers().stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponseDTO(userService.createUser(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponseDTO(userService.updateUser(id, userDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "User with id " + id + " deleted!"));
    }
}
