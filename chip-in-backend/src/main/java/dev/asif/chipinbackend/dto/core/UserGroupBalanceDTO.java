package dev.asif.chipinbackend.dto.core;

import dev.asif.chipinbackend.model.UserGroupBalance;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserGroupBalanceDTO {
    private Long userId;
    private BigDecimal totalPaidAmount;
    private BigDecimal totalShareAmount;

    public UserGroupBalanceDTO(UserGroupBalance balance) {
       this.userId = balance.getUser().getId();
       this.totalPaidAmount = balance.getTotalPaidAmount();
       this.totalShareAmount = balance.getTotalShareAmount();
    }
}
