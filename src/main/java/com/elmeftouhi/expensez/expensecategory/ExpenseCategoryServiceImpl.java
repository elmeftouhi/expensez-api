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
    public void updateLevel(Long id, ExpenseCategoryLevelDirection direction) {
        Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(id);
        if (expenseCategory.isPresent()){

            var allCategories = expenseCategoryRepository.findAllOrderByLevel();
            int currentIndex = allCategories.indexOf(expenseCategory.get());
            int totalElements = allCategories.size();

            if (totalElements > 1){
                // element is last
                if ((currentIndex + 1) == totalElements && direction.name().equals(ExpenseCategoryLevelDirection.UP.name())) {
                    allCategories.get(currentIndex).setLevel(currentIndex);
                    allCategories.get(currentIndex - 1).setLevel(currentIndex + 1);
                }

                // element is first
                if ( (currentIndex + 1) == 1  && direction.name().equals(ExpenseCategoryLevelDirection.DOWN.name()) ){
                    allCategories.get(currentIndex).setLevel(currentIndex + 2);
                    allCategories.get(currentIndex + 1).setLevel(currentIndex + 1);
                }

                // element is middle and Down
                if ( (currentIndex + 1) > 1 &&
                        (currentIndex + 1) < totalElements &&
                        direction.name().equals(ExpenseCategoryLevelDirection.DOWN.name())
                ){
                    allCategories.get(currentIndex).setLevel(currentIndex + 1);
                    allCategories.get(currentIndex + 1).setLevel(currentIndex - 1);
                }

                // element is middle and Up
                if ( (currentIndex + 1) > 1 &&
                        (currentIndex + 1) < totalElements &&
                        direction.name().equals(ExpenseCategoryLevelDirection.UP.name())
                ){
                    allCategories.get(currentIndex).setLevel(currentIndex );
                    allCategories.get(currentIndex - 1).setLevel(currentIndex + 1);
                }

                expenseCategoryRepository.saveAll(allCategories);

            }

        }
    }
}
