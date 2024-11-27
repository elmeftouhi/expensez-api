package com.elmeftouhi.expensez.expensecategory;

import com.elmeftouhi.expensez.expense.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense-category")
public class ExpenseCategoryController {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryRepository expenseCategoryRepository, ExpenseCategoryService expenseCategoryService, ExpenseRepository expenseRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping
    List<ExpenseCategoryResponse> getAllExpenseCategories(
            @RequestParam(required = false, defaultValue = "level", name = "order_by") String orderBy,
            @RequestParam(required = false, defaultValue = "false", name = "include_deleted") Boolean includeDeleted,
            @RequestParam(required = false, defaultValue = "false", name = "only_deleted") Boolean onlyDeleted
    ){
        List<ExpenseCategory> categories = expenseCategoryRepository.findAllByDeletedAtIsNullOrderByLevel() ;
        
        if(Boolean.TRUE.equals(includeDeleted))
            categories = expenseCategoryRepository.findAllOrderByLevel();
        
        if(Boolean.TRUE.equals(onlyDeleted))
            categories = expenseCategoryRepository.findAllByDeletedAtIsNotNullOrderByLevel();

        return categories.stream()
                .map(ExpenseCategoryResponse::new)
                .toList();
    }

    @PostMapping()
    ResponseEntity create(
            @RequestBody ExpenseCategoryResource expenseCategoryResource
    ){
        ExpenseCategory newCategory = new ExpenseCategory(
                expenseCategoryResource.name(),
                expenseCategoryResource.status());

        newCategory.setLevel(expenseCategoryRepository.findAll().size() + 1);

        expenseCategoryRepository.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense Category Created Successfully");
    }

    @PutMapping("/level/{id}")
    void updateLevel(
            @RequestParam(required = true) ExpenseCategoryLevelDirection direction,
            @PathVariable Long id
    ){
        expenseCategoryService.updateLevel(id, direction);
    }

    @PutMapping("/{id}")
    void update(
            @PathVariable Long id,
            @RequestBody ExpenseCategoryResource expenseCategoryResource
    ){
        expenseCategoryService.update(id, expenseCategoryResource);
    }

    @GetMapping("/{id}")
    ResponseEntity<ExpenseCategoryResponse> getById(
            @PathVariable Long id
    ){
        ExpenseCategory expenseCategory = expenseCategoryService.findExpenseCategoryById(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    new ExpenseCategoryResponse(expenseCategory)
            );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") Boolean hardDelete
    ){
        expenseCategoryService.delete(id, hardDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
