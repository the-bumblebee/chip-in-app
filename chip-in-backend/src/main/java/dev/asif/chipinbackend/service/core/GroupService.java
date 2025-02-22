package dev.asif.chipinbackend.service.core;

import dev.asif.chipinbackend.model.Group;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupService {
    Group getGroupById(Long id);
    Group createGroup(Group group);
    Group updateGroup(Long id, Group groupDetails);
    void deleteGroup(Long id);

    List<Group> getAllGroups();
    Group saveGroup(Group group);

    // void addUserToGroup(Long userId, Long groupId);
    // List<UserDTO> getUsersInGroup(Long groupId);
    // List<Payment> payments getPayments()
    // List<Expense> getExpenses()
}
