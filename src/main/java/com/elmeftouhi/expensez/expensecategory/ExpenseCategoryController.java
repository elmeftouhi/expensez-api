package com.elmeftouhi.expensez.expensecategory;

import com.elmeftouhi.expensez.expense.Expense;
import com.elmeftouhi.expensez.expense.ExpenseRepository;
import com.elmeftouhi.expensez.expense.ExpenseResource;
import com.elmeftouhi.expensez.expense.ExpenseResponse;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense-category")
public class ExpenseCategoryController {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseCategoryService expenseCategoryService;
    private final ExpenseRepository expenseRepository;

    public ExpenseCategoryController(ExpenseCategoryRepository expenseCategoryRepository, ExpenseCategoryService expenseCategoryService, ExpenseRepository expenseRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.expenseCategoryService = expenseCategoryService;
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    List<ExpenseCategoryResponse> getAllExpenseCategories(
            @RequestParam(required = false, defaultValue = "level") String order_by,
            @RequestParam(required = false, defaultValue = "false") Boolean include_deleted,
            @RequestParam(required = false, defaultValue = "false") Boolean only_deleted,
            @RequestParam(required = false, defaultValue = "false") Boolean include_expenses
    ){
        List<ExpenseCategory> categories = expenseCategoryRepository.findAllByDeletedAtIsNullOrderByLevel() ;
        
        if(include_deleted)
            categories = expenseCategoryRepository.findAllOrderByLevel();
        
        if(only_deleted)
            categories = expenseCategoryRepository.findAllByDeletedAtIsNotNullOrderByLevel();

        if (include_expenses){
            return categories.stream()
                    .map(category -> {
                        List<Expense> expenses = expenseRepository.findByExpenseCategory(category);
                        return new ExpenseCategoryResponse(category, expenses.stream().map(ExpenseResponse::new).toList());
                    }).toList();
        }

        return categories.stream()
                .map(ExpenseCategoryResponse::new)
                .toList();
    }

    @PostMapping()
    ResponseEntity create(@RequestBody ExpenseCategoryResource expenseCategoryResource){
        ExpenseCategory newCategory = new ExpenseCategory(
                expenseCategoryResource.name(),
                expenseCategoryResource.status());

        newCategory.setLevel(expenseCategoryRepository.findAll().size() + 1);

        expenseCategoryRepository.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense Category Created Successfully");
    }

    @PutMapping("/level/{id}")
    void updateLevel(@RequestParam(required = true) ExpenseCategoryLevelDirection direction,
                               @PathVariable Long id){
        expenseCategoryService.updateLevel(id, direction);
    }

    @PutMapping("/{id}")
    void update(
            @PathVariable Long id,
            @RequestBody ExpenseCategoryResource expenseCategoryResource
    ){
        expenseCategoryService.update(id, expenseCategoryResource);
    }

    @DeleteMapping("/{id}")
    void delete(
            @PathVariable Long id
    ){
        expenseCategoryService.delete(id);
    }

}
