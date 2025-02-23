package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.service.UserGroupOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/users")
public class UserGroupController {

    private final UserGroupOrchestrator userGroupOrchestrator;

    @Autowired
    public UserGroupController(UserGroupOrchestrator userGroupOrchestrator) {
        this.userGroupOrchestrator = userGroupOrchestrator;
    }

    @GetMapping
    public List<UserDTO> getUsersInGroup(@PathVariable Long groupId) {
        return userGroupOrchestrator.getUsersInGroup(groupId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        userGroupOrchestrator.addUserToGroup(groupId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
