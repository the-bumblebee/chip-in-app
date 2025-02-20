package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.UserDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.repository.GroupRepository;
import dev.asif.chipinbackend.repository.UserRepository;
import dev.asif.chipinbackend.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Set<UserDTO> getUsersInGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group with id " + groupId + " not found!"));
        return group.getUsers().stream()
                .map((user) -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
                .collect(Collectors.toSet());
    }

    @Override
    public Group updateGroup(Long id, Group groupDetails) {
        return groupRepository.findById(id).map((group) -> {
            group.setName(groupDetails.getName());
            return groupRepository.save(group);
        }).orElseThrow(() -> new RuntimeException("Group with id " + id + " not found!"));
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public void addUserToGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(("User with id " + userId + " not found!")));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException(("Group with id " + groupId + " not found!")));
        group.addUser(user);
        groupRepository.save(group);
    }
}
