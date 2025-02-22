package dev.asif.chipinbackend.controller;

import dev.asif.chipinbackend.dto.core.ExpenseDTO;
import dev.asif.chipinbackend.dto.ExpenseRequestDTO;
import dev.asif.chipinbackend.service.ExpenseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/expenses")
public class ExpenseController {

    private final ExpenseManager expenseManager;

    @Autowired
    public ExpenseController(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    @PostMapping
    public ExpenseDTO createExpense(@PathVariable Long groupId, @RequestBody ExpenseRequestDTO expenseRequestDTO) {
        return expenseManager.createExpense(groupId, expenseRequestDTO);
    }
}
