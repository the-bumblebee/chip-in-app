package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.UserPayment;
import dev.asif.chipinbackend.repository.GroupRepository;
import dev.asif.chipinbackend.repository.UserPaymentRepository;
import dev.asif.chipinbackend.service.GroupService;
import dev.asif.chipinbackend.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserPaymentServiceImpl implements UserPaymentService {

    GroupService groupService;
    UserPaymentRepository userPaymentRepository;

    @Autowired
    public UserPaymentServiceImpl(GroupService groupService, UserPaymentRepository userPaymentRepository) {
        this.groupService = groupService;
        this.userPaymentRepository = userPaymentRepository;
    }

    @Override
    public List<UserPayment> getAllPaymentsInGroup(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return List.of();
    }

    @Override
    public UserPayment createPayment(Long groupId, UserPayment payment) {
        return null;
    }
}
