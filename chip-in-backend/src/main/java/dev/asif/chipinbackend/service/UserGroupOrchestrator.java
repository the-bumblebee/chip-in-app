package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.core.UserDTO;

import java.util.List;

public interface UserGroupOrchestrator {
    List<UserDTO> getUsersInGroup(Long groupId);
    void addUserToGroup(Long groupId, Long userId);
}
