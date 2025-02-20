package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
