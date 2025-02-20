package dev.asif.chipinbackend.service.impl;

import dev.asif.chipinbackend.dto.UserPaymentDTO;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserGroupBalance;
import dev.asif.chipinbackend.model.UserPayment;
import dev.asif.chipinbackend.repository.UserGroupBalanceRepository;
import dev.asif.chipinbackend.repository.UserPaymentRepository;
import dev.asif.chipinbackend.service.BalanceService;
import dev.asif.chipinbackend.service.GroupService;
import dev.asif.chipinbackend.service.UserPaymentService;
import dev.asif.chipinbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class UserPaymentServiceImpl implements UserPaymentService {

    UserService userService;
    GroupService groupService;
    BalanceService balanceService;
    UserPaymentRepository userPaymentRepository;

    @Autowired
    public UserPaymentServiceImpl(
            UserService userService,
            GroupService groupService,
            BalanceService balanceService,
            UserPaymentRepository userPaymentRepository) {
        this.userService = userService;
        this.groupService = groupService;
        this.balanceService = balanceService;
        this.userPaymentRepository = userPaymentRepository;
    }

    @Override
    public List<UserPaymentDTO> getAllPaymentsInGroup(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return group.getPayments().stream()
                .map(UserPaymentDTO::new)
                .toList();
    }

    @Override
    public UserPayment createPayment(Long groupId, UserPaymentDTO payment) {
        UserGroupBalance payerBalance = balanceService.getBalanceByGroupAndUser(groupId, payment.getPayerId());
        UserGroupBalance payeeBalance = balanceService.getBalanceByGroupAndUser(groupId, payment.getPayeeId());

        payerBalance.updateBalance(payment.getAmount(), BigDecimal.ZERO);
        payeeBalance.updateBalance(payment.getAmount().negate(), BigDecimal.ZERO);


    }
}
