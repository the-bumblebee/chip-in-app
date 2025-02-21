package dev.asif.chipinbackend.dto.core;

import dev.asif.chipinbackend.model.UserGroupBalance;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserGroupBalanceDTO {
    private Long id;
    private Long userId;
    private Long groupId;
    private BigDecimal totalPaidAmount;
    private BigDecimal totalShareAmount;

    public UserGroupBalanceDTO(UserGroupBalance balance) {
       this.id = balance.getId();
       this.userId = balance.getUser().getId();
       this.groupId = balance.getGroup().getId();
       this.totalPaidAmount = balance.getTotalPaidAmount();
       this.totalShareAmount = balance.getTotalShareAmount();
    }
}
