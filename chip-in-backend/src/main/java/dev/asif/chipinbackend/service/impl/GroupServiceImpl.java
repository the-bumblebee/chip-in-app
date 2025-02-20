package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.UserDTO;
import dev.asif.chipinbackend.exception.ResourceNotFoundException;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.repository.GroupRepository;
import dev.asif.chipinbackend.service.GroupService;
import dev.asif.chipinbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final UserService userService;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(UserService userService, GroupRepository groupRepository) {
        this.userService = userService;
        this.groupRepository = groupRepository;
    }

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group with id " + id + " does not exist!"));
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Set<UserDTO> getUsersInGroup(Long groupId) {
        Group group = getGroupById(groupId);
        return group.getUsers().stream()
                .map((user) -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
                .collect(Collectors.toSet());
    }

    @Override
    public Group updateGroup(Long id, Group groupDetails) {
        Group group = getGroupById(id);
        group.setName(groupDetails.getName());
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public void addUserToGroup(Long userId, Long groupId) {
        User user = userService.getUserById(userId);
        Group group = getGroupById(groupId);
        group.addUser(user);
        groupRepository.save(group);
    }
}
