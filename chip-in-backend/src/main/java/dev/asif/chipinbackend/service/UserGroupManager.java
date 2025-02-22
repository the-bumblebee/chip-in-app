package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.model.User;

import java.util.List;

public interface UserGroupManager {
    List<UserDTO> getUsersInGroup(Long groupId);
    void addUserToGroup(Long groupId, Long userId);
}
