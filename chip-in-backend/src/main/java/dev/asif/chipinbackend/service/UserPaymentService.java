package dev.asif.chipinbackend.service;

import dev.asif.chipinbackend.dto.UserPaymentDTO;
import dev.asif.chipinbackend.model.UserPayment;

import java.util.List;

public interface UserPaymentService {
    List<UserPaymentDTO> getAllPaymentsInGroup(Long groupId);
    UserPayment createPayment(Long groupId, UserPayment payment);
}
