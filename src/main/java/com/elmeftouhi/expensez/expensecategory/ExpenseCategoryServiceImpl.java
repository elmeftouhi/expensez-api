package com.elmeftouhi.expensez.expensecategory;

import com.elmeftouhi.expensez.exception.ExpenseCategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService{

    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategoryServiceImpl(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public void updateLevel(Long id, ExpenseCategoryLevelDirection direction) {
        Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findExpenseCategoryById(id);
        if (expenseCategory.isPresent()){

            var allCategories = expenseCategoryRepository.findAllOrderByLevel();
            int currentIndex = allCategories.indexOf(expenseCategory.get());
            int totalElements = allCategories.size();

            if (totalElements > 1){
                // element is last
                if ((currentIndex + 1) == totalElements && direction.name().equals(ExpenseCategoryLevelDirection.UP.name())) {
                    allCategories.get(currentIndex).decrementLevel();
                    allCategories.get(currentIndex - 1).incrementLevel();
                }

                // element is first
                if ( (currentIndex + 1) == 1  && direction.name().equals(ExpenseCategoryLevelDirection.DOWN.name()) ){
                    allCategories.get(currentIndex).incrementLevel();
                    allCategories.get(currentIndex + 1).decrementLevel();
                }

                // element is middle and Down
                if ( (currentIndex + 1) > 1 &&
                        (currentIndex + 1) < totalElements &&
                        direction.name().equals(ExpenseCategoryLevelDirection.DOWN.name())
                ){
                    allCategories.get(currentIndex).incrementLevel();
                    allCategories.get(currentIndex + 1).decrementLevel();
                }

                // element is middle and Up
                if ( (currentIndex + 1) > 1 &&
                        (currentIndex + 1) < totalElements &&
                        direction.name().equals(ExpenseCategoryLevelDirection.UP.name())
                ){
                    allCategories.get(currentIndex).decrementLevel();
                    allCategories.get(currentIndex - 1).incrementLevel();
                }

                expenseCategoryRepository.saveAll(allCategories);

            }

        }else
            throw new ExpenseCategoryNotFoundException("Expense Category with id : " + id + " not found");
    }

    @Override
    public void update(Long id, ExpenseCategoryResource expenseCategoryResource) {
        Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(id);
        if (expenseCategory.isPresent()){
            expenseCategory.get().setName(expenseCategoryResource.name() == null? expenseCategory.get().getName() : expenseCategoryResource.name() );
            expenseCategory.get().setExpenseCategoryStatus(expenseCategoryResource.status() == null? expenseCategory.get().getExpenseCategoryStatus() : expenseCategoryResource.status() );
            expenseCategory.get().setUpdated_at(LocalDateTime.now());
            expenseCategory.get().setUpdated_by(69);
            expenseCategoryRepository.save(expenseCategory.get());
        }else
            throw new ExpenseCategoryNotFoundException("Expense Category with id : " + id + " not found");
    }

    @Override
    public void delete(Long id, Boolean hard_delete) {
        Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findExpenseCategoryById(id);
        if (expenseCategory.isPresent()){
            if (hard_delete){
                expenseCategoryRepository.delete(expenseCategory.get());
            }else {
                expenseCategory.get().setDeletedAt(LocalDateTime.now());
                expenseCategory.get().setDeletedBy(69);
                expenseCategoryRepository.save(expenseCategory.get());
            }
        }else
            throw new ExpenseCategoryNotFoundException("Expense Category with id : " + id + " not found");
    }

    @Override
    public ExpenseCategory findExpenseCategoryById(Long id) {
        Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findExpenseCategoryById(id);
        if (expenseCategory.isPresent()){
            return expenseCategory.get();
        }else
            throw new ExpenseCategoryNotFoundException("Expense Category with id : " + id + " not found");
    }
}
