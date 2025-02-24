package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.service.UserGroupOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        userGroupOrchestrator.addUserToGroup(groupId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "User added to group!"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> removeUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        userGroupOrchestrator.removeUserFromGroup(groupId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "User remove from group!"));
    }
}
