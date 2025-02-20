package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.model.UserGroupBalance;
import lombok.Data;

@Data
public class UserGroupBalanceDTO {
    private Long userId;
    private double totalPaidAmount;
    private double totalShareAmount;

    public UserGroupBalanceDTO(UserGroupBalance balance) {
       this.userId = balance.getUser().getId();
       this.totalPaidAmount = balance.getTotalPaidAmount();
       this.totalShareAmount = balance.getTotalShareAmount();
    }
}
