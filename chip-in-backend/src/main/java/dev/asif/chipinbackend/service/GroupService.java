package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.UserDTO;
import dev.asif.chipinbackend.model.Group;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupService {
    Group createGroup(Group group);
    Group getGroupById(Long id);
    List<Group> getAllGroups();
    Set<UserDTO> getUsersInGroup(Long groupId);
    Group updateGroup(Long id, Group groupDetails);
    void deleteGroup(Long id);
    void addUserToGroup(Long userId, Long groupId);
}
