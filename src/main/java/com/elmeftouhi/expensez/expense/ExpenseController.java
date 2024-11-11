package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import com.elmeftouhi.expensez.expensecategory.ExpenseCategoryRepository;
import org.hibernate.query.QueryParameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("expense")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository, ExpenseService expenseService) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.expenseService = expenseService;
    }

    @GetMapping
    List<ExpenseResponse> getAll(
            @RequestParam(required = false, defaultValue = "false") Boolean include_deleted,
            @RequestParam(required = false, defaultValue = "false") Boolean only_deleted
    ){
        if(only_deleted)
            return expenseRepository.findAllByDeletedAtIsNotNull()
                    .stream()
                    .map(ExpenseResponse::new)
                    .toList();

        if(include_deleted)
            return expenseRepository.findAll()
                    .stream()
                    .map(ExpenseResponse::new)
                    .toList();
        else
            return expenseRepository.findAllByDeletedAtIsNullOrderByDateExpenseDesc()
                    .stream()
                    .map(ExpenseResponse::new)
                    .toList();
    }

    @PostMapping
    void create(@RequestBody ExpenseResource expenseResource){
        System.out.println(expenseResource);
        ExpenseCategory category = expenseCategoryRepository.findById(expenseResource.expenseCategoryId()).orElse(null);
        expenseRepository.save(
                new Expense(
                        expenseResource.amount(),
                        expenseResource.description(),
                        expenseResource.expenseDate(),
                        category
                        )
        );
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id){
        expenseService.deleteExpense(id);
    }
}
