package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.service.UserGroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/users")
public class UserGroupController {

    private final UserGroupManager userGroupManager;

    @Autowired
    public UserGroupController(UserGroupManager userGroupManager) {
        this.userGroupManager = userGroupManager;
    }

    @GetMapping
    public List<UserDTO> getUsersInGroup(@PathVariable Long groupId) {
        return userGroupManager.getUsersInGroup(groupId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        userGroupManager.addUserToGroup(groupId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
