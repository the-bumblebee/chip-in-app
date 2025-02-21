package dev.asif.chipinbackend.service.core;

import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserPayment;

import java.math.BigDecimal;
import java.util.List;

public interface UserPaymentService {
    UserPayment getUserPaymentById(Long id);
    UserPayment createUserPayment(User payer, User payee, Group group, BigDecimal amount);
    UserPayment updateUserPayment(Long id, UserPayment userPaymentDetails);
    void deleteUserPayment(Long id);

    List<UserPayment> getAllUserPaymentsByGroup(Group group);

    // List<UserPaymentDTO> getAllPaymentsInGroup(Long groupId);
    // UserPayment createPayment(Long groupId, UserPaymentDTO payment);
}
