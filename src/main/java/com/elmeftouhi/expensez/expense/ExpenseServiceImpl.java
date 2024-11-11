package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import com.elmeftouhi.expensez.expensecategory.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public void deleteExpense(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()){
            expense.get().setDeletedAt(LocalDateTime.now());
            expense.get().setDeletedBy(69);
            expenseRepository.save(expense.get());
        }

    }

    @Override
    public void update(Long id, ExpenseResource expenseResource) {
        Optional<Expense> expense = expenseRepository.findById(id);

        if (expense.isPresent()){
            expense.get().setAmount(expenseResource.amount() == null? expense.get().getAmount(): expenseResource.amount());
            expense.get().setDescription(expenseResource.description() == null? expense.get().getDescription(): expenseResource.description());
            expense.get().setDateExpense(expenseResource.expenseDate() == null? expense.get().getDateExpense(): expenseResource.expenseDate());
            if (expenseResource.expenseCategoryId() != null){
                Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(expenseResource.expenseCategoryId());
                expenseCategory.ifPresent(category -> expense.get().setExpenseCategory(category));
            }
            expense.get().setUpdatedAt(LocalDateTime.now());
            expense.get().setUpdatedBy(69);
            expenseRepository.save(expense.get());
        }
    }
}
