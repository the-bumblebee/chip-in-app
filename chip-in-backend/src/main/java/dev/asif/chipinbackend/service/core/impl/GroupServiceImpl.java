package dev.asif.chipinbackend.service.core.impl;

import dev.asif.chipinbackend.exception.ResourceNotFoundException;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.repository.GroupRepository;
import dev.asif.chipinbackend.service.core.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group with id " + id + " does not exist!"));
    }

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }


    // @Override
    // public Set<UserDTO> getUsersInGroup(Long groupId) {
    //     Group group = getGroupById(groupId);
    //     return group.getUsers().stream()
    //             .map((user) -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
    //             .collect(Collectors.toSet());
    // }


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
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // @Override
    // public void addUserToGroup(Long userId, Long groupId) {
    //     User user = userService.getUserById(userId);
    //     Group group = getGroupById(groupId);
    //     group.addUser(user);
    //     groupRepository.save(group);
    // }
}
