package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    Group createGroup(Group group);
    Optional<Group> getGroupById(Long id);
    List<Group> getAllGroups();
    Group updateGroup(Long id, Group groupDetails);
    void deleteGroup(Long id);
}
