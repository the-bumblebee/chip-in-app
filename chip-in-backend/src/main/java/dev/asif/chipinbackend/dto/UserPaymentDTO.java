package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.model.UserPayment;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserPaymentDTO {
    private Long id;
    private Long payerId;
    private Long payeeId;
    private Long groupId;
    private BigDecimal amount;

    public UserPaymentDTO(UserPayment payment) {
        this.id = payment.getId();
        this.payerId = payment.getPayer().getId();
        this.payeeId = payment.getPayee().getId();
        this.groupId = payment.getGroup().getId();
        this.amount = payment.getAmount();
    }
}
