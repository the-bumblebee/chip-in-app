package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.repository.GroupRepository;
import dev.asif.chipinbackend.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
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
}
