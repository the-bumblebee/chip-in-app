package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.GroupResponseDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.service.core.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupResponseDTO> getAllGroups() {
        return groupService.getAllGroups().stream()
                .map(GroupResponseDTO::new)
                .toList();
    }

    @PostMapping
    public ResponseEntity<GroupResponseDTO> createGroup(@RequestBody Group group) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GroupResponseDTO(groupService.createGroup(group)));
    }
}
