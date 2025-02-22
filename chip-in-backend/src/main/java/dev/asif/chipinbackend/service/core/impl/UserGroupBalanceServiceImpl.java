package dev.asif.chipinbackend.service.core.impl;

import dev.asif.chipinbackend.exception.ResourceNotFoundException;
import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserGroupBalance;
import dev.asif.chipinbackend.repository.UserGroupBalanceRepository;
import dev.asif.chipinbackend.service.core.UserGroupBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserGroupBalanceServiceImpl implements UserGroupBalanceService {

    private final UserGroupBalanceRepository userGroupBalanceRepository;

    @Autowired
    public UserGroupBalanceServiceImpl(UserGroupBalanceRepository userGroupBalanceRepository) {
        this.userGroupBalanceRepository = userGroupBalanceRepository;
    }

    @Override
    public UserGroupBalance getUserGroupBalanceById(Long id) {
        return userGroupBalanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserGroupBalance with id " + id + " not found!"));
    }

    @Override
    public UserGroupBalance createUserGroupBalance(Group group, User user, BigDecimal totalPaidAmount, BigDecimal totalShareAmount) {
        UserGroupBalance userGroupBalance = new UserGroupBalance();
        userGroupBalance.setGroup(group);
        userGroupBalance.setUser(user);
        userGroupBalance.setTotalPaidAmount(totalPaidAmount);
        userGroupBalance.setTotalShareAmount(totalShareAmount);
        return userGroupBalanceRepository.save(userGroupBalance);
    }

    @Override
    public UserGroupBalance updateUserGroupBalance(Long id, UserGroupBalance userGroupBalanceDetails) {
        UserGroupBalance userGroupBalance = getUserGroupBalanceById(id);
        userGroupBalance.setTotalPaidAmount(userGroupBalanceDetails.getTotalPaidAmount());
        userGroupBalance.setTotalShareAmount(userGroupBalanceDetails.getTotalShareAmount());
        return userGroupBalanceRepository.save(userGroupBalance);
    }

    @Override
    public void deleteUserGroupBalance(Long id) {
        userGroupBalanceRepository.deleteById(id);
    }

    @Override
    public UserGroupBalance getBalanceByGroupAndUser(Group group, User user) {
        return userGroupBalanceRepository.findByGroupAndUser(group, user)
                .orElseThrow(() -> new ResourceNotFoundException("Balance resource does not exist for the specific user and group combo!"));
    }

    @Override
    public List<UserGroupBalance> getBalancesByGroup(Group group) {
        return userGroupBalanceRepository.findByGroup(group);
    }

    @Override
    public UserGroupBalance getOrNewBalance(Group group, User user) {
        return userGroupBalanceRepository.findByGroupAndUser(group, user)
                .orElse(new UserGroupBalance(group, user, BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @Override
    public UserGroupBalance adjustBalance(Long id, BigDecimal paidAmount, BigDecimal shareAmount) {
        UserGroupBalance userGroupBalance = getUserGroupBalanceById(id);
        userGroupBalance.updateBalance(paidAmount, shareAmount);
        return userGroupBalanceRepository.save(userGroupBalance);
    }

    @Override
    public UserGroupBalance saveUserGroupBalance(UserGroupBalance userGroupBalance) {
        return userGroupBalanceRepository.save(userGroupBalance);
    }
}
