package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserGroupBalance;
import dev.asif.chipinbackend.service.UserGroupOrchestrator;
import dev.asif.chipinbackend.service.core.GroupService;
import dev.asif.chipinbackend.service.core.UserGroupBalanceService;
import dev.asif.chipinbackend.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class UserGroupOrchestratorImpl implements UserGroupOrchestrator {

    private final UserService userService;
    private final GroupService groupService;
    private final UserGroupBalanceService userGroupBalanceService;

    @Autowired
    public UserGroupOrchestratorImpl(
            UserService userService,
            GroupService groupService,
            UserGroupBalanceService userGroupBalanceService) {
        this.userService = userService;
        this.groupService = groupService;
        this.userGroupBalanceService = userGroupBalanceService;
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

    @Override
    public void removeUserFromGroup(Long groupId, Long userId) {
        Group group = groupService.getGroupById(groupId);
        User user = userService.getUserById(userId);
        UserGroupBalance userGroupBalance = userGroupBalanceService.getBalanceByGroupAndUser(group, user);
        if (userGroupBalance.getNetBalance().equals(BigDecimal.ZERO)) {
            throw new RuntimeException("User with non-zero balance cannot be deleted!");
        }
        group.removeUser(user);
        group = groupService.saveGroup(group);
        user = userService.saveUser(user);
    }
}
