package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.core.ExpenseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;
import dev.asif.chipinbackend.service.ExpenseHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/expenses")
public class ExpenseController {

    private final ExpenseHandlerService expenseService;

    @Autowired
    public ExpenseController(ExpenseHandlerService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ExpenseDTO createExpense(@PathVariable Long groupId, @RequestBody ExpenseRequestDTO request) {
        return expenseService.createExpense(groupId, request);
    }
}
