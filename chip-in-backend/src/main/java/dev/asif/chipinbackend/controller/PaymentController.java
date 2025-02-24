package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.ExpenseResponseDTO;
import dev.asif.chipinbackend.dto.PaymentRequestDTO;
import dev.asif.chipinbackend.dto.PaymentResponseDTO;
import dev.asif.chipinbackend.service.PaymentOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups/{groupId}/payments")
public class PaymentController {

    private final PaymentOrchestrator paymentOrchestrator;

    @Autowired
    public PaymentController(PaymentOrchestrator paymentOrchestrator) {
        this.paymentOrchestrator = paymentOrchestrator;
    }

    @GetMapping
    public List<PaymentResponseDTO> getAllPaymentsInGroup(@PathVariable Long groupId) {
        return paymentOrchestrator.getAllPaymentsInGroup(groupId);
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@PathVariable Long groupId, @RequestBody PaymentRequestDTO paymentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentOrchestrator.createPayment(groupId, paymentRequestDTO));
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable Long groupId, @PathVariable Long paymentId, @RequestBody PaymentRequestDTO paymentRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(paymentOrchestrator.updatePayment(groupId, paymentId, paymentRequestDTO));
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Map<String, String>> deletePayment(@PathVariable Long groupId, @PathVariable Long paymentId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "UserPayment with id" + paymentId + " deleted!"));
    }
}
