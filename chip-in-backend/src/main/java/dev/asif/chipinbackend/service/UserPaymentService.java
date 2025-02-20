package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.model.UserPayment;

import java.util.List;

public interface UserPaymentService {
    List<UserPayment> getAllPaymentsInGroup(Long groupId);
    UserPayment createPayment(Long groupId, UserPayment payment);
}
