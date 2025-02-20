package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.ExpenseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;
import dev.asif.chipinbackend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ExpenseDTO createExpense(@PathVariable Long groupId, @RequestBody ExpenseRequestDTO request) {
        return expenseService.createExpense(groupId, request);
    }
}
