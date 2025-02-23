package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.ExpenseResponseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;
import dev.asif.chipinbackend.service.ExpenseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups/{groupId}/expenses")
public class ExpenseController {

    private final ExpenseManager expenseManager;

    @Autowired
    public ExpenseController(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    @GetMapping
    public List<ExpenseResponseDTO> getAllExpensesInGroup(@PathVariable Long groupId) {
        return expenseManager.getAllExpensesInGroup(groupId);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@PathVariable Long groupId, @RequestBody ExpenseRequestDTO expenseRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(expenseManager.createExpense(groupId, expenseRequestDTO));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable Long groupId, @PathVariable Long expenseId, @RequestBody ExpenseRequestDTO expenseRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(expenseManager.updateExpense(groupId, expenseId, expenseRequestDTO));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Map<String, String>> deleteExpense(@PathVariable Long groupId, @PathVariable Long expenseId) {
        expenseManager.deleteExpense(groupId, expenseId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Expense with id " + expenseId + " deleted!"));
    }
}
