package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.PaymentRequestDTO;
import dev.asif.chipinbackend.dto.PaymentResponseDTO;
import dev.asif.chipinbackend.service.PaymentOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/payments")
public class PaymentController {

    private final PaymentOrchestrator paymentOrchestrator;

    @Autowired
    public PaymentController(PaymentOrchestrator paymentOrchestrator) {
        this.paymentOrchestrator = paymentOrchestrator;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@PathVariable Long groupId, @RequestBody PaymentRequestDTO paymentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentOrchestrator.createPayment(groupId, paymentRequestDTO));
    }
}
