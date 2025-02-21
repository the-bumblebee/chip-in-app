package dev.asif.chipinbackend.service.core;

import dev.asif.chipinbackend.model.Group;
import dev.asif.chipinbackend.model.User;
import dev.asif.chipinbackend.model.UserGroupBalance;

import java.math.BigDecimal;

public interface UserGroupBalanceService {
    UserGroupBalance getUserGroupBalanceById(Long id);
    UserGroupBalance createUserGroupBalance(Group group, User user, BigDecimal totalPaidAmount, BigDecimal totalShareAmount);
    UserGroupBalance updateUserGroupBalance(Long id, UserGroupBalance userGroupBalanceDetails);
    void deleteUserGroupBalance(Long id);

    UserGroupBalance getBalanceByGroupAndUser(Group group, User user);
    UserGroupBalance adjustBalance(Long id, BigDecimal paidAmount, BigDecimal shareAmount);
}
