package dev.asif.chipinbackend.dto;

import dev.asif.chipinbackend.dto.core.UserDTO;
import dev.asif.chipinbackend.model.UserPayment;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentResponseDTO {
    private Long id;
    private UserDTO payer;
    private UserDTO payee;
    private BigDecimal amount;

    public PaymentResponseDTO(UserPayment payment) {
        this.id = payment.getId();
        this.payer = new UserDTO(payment.getPayer());
        this.payee = new UserDTO(payment.getPayee());
        this.amount = payment.getAmount();
    }
}
