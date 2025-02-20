package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.UserDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{groupId}/users")
    public Set<UserDTO> getUsersInGroup(@PathVariable Long groupId){
        return groupService.getUsersInGroup(groupId);
    }

    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<Void> addUserToGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        groupService.addUserToGroup(userId, groupId);
        return ResponseEntity.status(201).build();
    }
}
