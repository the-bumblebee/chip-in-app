package dev.asif.chipinbackend.service.core.impl;

import dev.asif.chipinbackend.exception.ResourceNotFoundException;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserPayment;
import dev.asif.chipinbackend.repository.UserPaymentRepository;
import dev.asif.chipinbackend.service.core.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    private final UserPaymentRepository userPaymentRepository;

    @Autowired
    public UserPaymentServiceImpl(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }

    @Override
    public UserPayment getUserPaymentById(Long id) {
        return userPaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User payment with id " + id + " not found!"));
    }

    @Override
    public UserPayment createUserPayment(User payer, User payee, Group group, BigDecimal amount) {
        UserPayment userPayment = new UserPayment();
        userPayment.setPayer(payer);
        userPayment.setPayee(payee);
        userPayment.setGroup(group);
        userPayment.setAmount(amount);
        return userPaymentRepository.save(userPayment);
        // Group group = groupService.getGroupById(groupId);
        // User payer = userService.getUserById(paymentDTO.getPayerId());
        // User payee = userService.getUserById(paymentDTO.getPayeeId());
        //
        // UserGroupBalance payerBalance = balanceService.getBalanceByGroupIdAndUserId(groupId, paymentDTO.getPayerId());
        // UserGroupBalance payeeBalance = balanceService.getBalanceByGroupIdAndUserId(groupId, paymentDTO.getPayeeId());
        //
        // payerBalance.updateBalance(paymentDTO.getAmount(), BigDecimal.ZERO);
        // payeeBalance.updateBalance(paymentDTO.getAmount().negate(), BigDecimal.ZERO);
        //
        // balanceService.saveBalance(payerBalance);
        // balanceService.saveBalance(payeeBalance);
        //
        // UserPayment payment = new UserPayment();
        // payment.setPayer(payer);
        // payment.setPayee(payee);
        // payment.setGroup(group);
        // payment.setAmount(paymentDTO.getAmount());
        //
        // return userPaymentRepository.save(payment);
    }

    @Override
    public UserPayment updateUserPayment(Long id, UserPayment userPaymentDetails) {
        UserPayment userPayment = getUserPaymentById(id);
        userPayment.setAmount(userPaymentDetails.getAmount());
        userPayment.setPayer(userPaymentDetails.getPayer());
        userPayment.setPayee(userPaymentDetails.getPayee());
        return userPaymentRepository.save(userPayment);
    }

    @Override
    public void deleteUserPayment(Long id) {
        userPaymentRepository.deleteById(id);
    }

    @Override
    public List<UserPayment> getAllUserPaymentsByGroup(Group group) {
        return group.getPayments();
    }

    @Override
    public UserPayment saveUserPayment(UserPayment userPayment) {
        return userPaymentRepository.save(userPayment);
    }
}
