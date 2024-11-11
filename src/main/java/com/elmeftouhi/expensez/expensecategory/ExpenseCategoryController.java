package com.elmeftouhi.expensez.expensecategory;

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

    public ExpenseCategoryController(ExpenseCategoryRepository expenseCategoryRepository, ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping
    List<ExpenseCategoryResponse> getAllExpenseCategories(
            @RequestParam(required = false, defaultValue = "level") String order_by
    ){
        List<ExpenseCategory> categories;
        if(order_by.equals("level"))
            categories = expenseCategoryRepository.findAllOrderByLevel();
        else
            categories = expenseCategoryRepository.findAllByOrderByCreatedAtDesc();

        return categories.stream()
                .map(ExpenseCategoryResponse::new)
                .toList();
    }

    @PostMapping()
    ResponseEntity create(@RequestBody ExpenseCategoryResource expenseCategoryResource){
        expenseCategoryRepository.save(new ExpenseCategory(
                expenseCategoryResource.name(),
                expenseCategoryResource.status(),
                expenseCategoryResource.level()));
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense Category Created Successfully");
    }

    @PutMapping("/level/{id}")
    void updateLevel(@RequestParam(required = true) ExpenseCategoryLevelDirection direction,
                               @PathVariable Long id){
        expenseCategoryService.updateLevel(id, direction);
    }

}
