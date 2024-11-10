package com.elmeftouhi.expensez.expensecategory;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService{

    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategoryServiceImpl(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public Integer updateLevel(Long id, Integer level) {
        Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(id);
        if (expenseCategory.isPresent()){
            if (level == 1){

            }
        }

        return 0;
    }
}
