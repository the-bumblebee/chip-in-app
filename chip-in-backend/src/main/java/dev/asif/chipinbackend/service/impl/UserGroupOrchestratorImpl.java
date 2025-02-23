package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.service.UserGroupOrchestrator;
import dev.asif.chipinbackend.service.core.GroupService;
import dev.asif.chipinbackend.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserGroupOrchestratorImpl implements UserGroupOrchestrator {

    private final UserService userService;
    private final GroupService groupService;

    @Autowired
    public UserGroupOrchestratorImpl(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public List<UserDTO> getUsersInGroup(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return userService.getUsersByGroup(group).stream()
                .map(UserDTO::new)
                .toList();
    }

    @Override
    public void addUserToGroup(Long groupId, Long userId) {
        Group group = groupService.getGroupById(groupId);
        User user = userService.getUserById(userId);
        group.addUser(user);
        group = groupService.saveGroup(group);
        user = userService.saveUser(user);
    }
}
