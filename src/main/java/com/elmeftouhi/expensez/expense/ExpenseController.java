package com.elmeftouhi.expensez.expense;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("expense")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseRepository expenseRepository, ExpenseService expenseService) {
        this.expenseRepository = expenseRepository;
        this.expenseService = expenseService;
    }

    @GetMapping
    List<ExpenseResponse> getAll(
            @RequestParam(required = false, defaultValue = "false") Boolean include_deleted,
            @RequestParam(required = false, defaultValue = "false") Boolean only_deleted,
            @RequestParam(required = false, defaultValue = "") String order_by,
            @RequestParam(required = false, defaultValue = "") String description,
            @RequestParam(required = false, defaultValue = "") Long expenseCategoryId,
            @RequestParam(required = false, defaultValue = "") String from,
            @RequestParam(required = false, defaultValue = "") String to
    ){
        return expenseService.getAll(
                include_deleted,
                order_by,
                only_deleted,
                description,
                expenseCategoryId,
                from,
                to
        );
    }

    @PostMapping
    ResponseEntity<Void> create(
            @RequestBody ExpenseResource expenseResource
    ){
        expenseService.save(expenseResource);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") Boolean hard_delete
    ){
        expenseService.deleteExpense(id, hard_delete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    Optional<Expense> findById(
            @PathVariable Long id
    ){
        return expenseRepository.findById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody ExpenseResource expenseResource){
        expenseService.update(id, expenseResource);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
