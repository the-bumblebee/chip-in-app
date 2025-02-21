package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.model.UserGroupBalance;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GroupBalanceResponseDTO {
    private Long userId;
    private BigDecimal totalPaidAmount;
    private BigDecimal totalShareAmount;

    public GroupBalanceResponseDTO(UserGroupBalance userGroupBalance) {
        this.userId = userGroupBalance.getUser().getId();
        this.totalPaidAmount = userGroupBalance.getTotalPaidAmount();
        this.totalShareAmount = userGroupBalance.getTotalShareAmount();
    }
}
