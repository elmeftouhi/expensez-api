package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.exception.ExpenseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
            @RequestParam(required = false, defaultValue = "false", name = "include_deleted") Boolean includeDeleted,
            @RequestParam(required = false, defaultValue = "false", name = "only_deleted") Boolean onlyDeleted,
            @RequestParam(required = false, defaultValue = "") String description,
            @RequestParam(required = false, defaultValue = "") Long expenseCategoryId,
            @RequestParam(required = false, defaultValue = "") String from,
            @RequestParam(required = false, defaultValue = "") String to
    ){
        return expenseService.getAll(
                includeDeleted,
                onlyDeleted,
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
    Expense findById(
            @PathVariable Long id
    ){
        return expenseRepository.findById(id).orElseThrow(ExpenseNotFoundException::new);
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody ExpenseResource expenseResource){
        expenseService.update(id, expenseResource);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/report")
    public ReportResponse getReport(
            @RequestParam(required = false, name = "year") String year,
            @RequestParam(required = false, name = "month") String month,
            @RequestParam(required = false, name = "expense_category_id") Long expenseCategoryId
    ){
        return expenseService.getTotalByMonthForAYear(year);

    }
}
