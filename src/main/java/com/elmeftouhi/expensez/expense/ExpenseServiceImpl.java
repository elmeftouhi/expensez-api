package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.exception.ExpenseCategoryNotFoundException;
import com.elmeftouhi.expensez.exception.ExpenseNotFoundException;
import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import com.elmeftouhi.expensez.expensecategory.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    public List<ExpenseResponse> getAll(
            Boolean include_deleted,
            String order_by,
            Boolean only_deleted,
            String description,
            Long expenseCategoryId,
            String from,
            String to
    ) {

        List<Expense> expenses = List.of();

        if(only_deleted){
            expenses = expenseRepository.findAllByDeletedAtIsNotNullOrderByDateExpenseDesc();
        } else if (include_deleted) {
            expenses = expenseRepository.findAllByOrderByDateExpenseDesc();
        }else {
            expenses = expenseRepository.findAllByDeletedAtIsNullOrderByDateExpenseDesc();
        }

//        if (year != null){
//            expenses = expenses
//                    .stream()
//                    .filter(e -> e.getDateExpense().getYear() == year)
//                    .toList();
//        }
//
//        if (month != null){
//            expenses = expenses
//                    .stream()
//                    .filter(e -> e.getDateExpense().getMonthValue() == month)
//                    .toList();
//        }

        if (description != null){
            expenses = expenses
                    .stream()
                    .filter(e -> e.getDescription().contains(description))
                    .toList();
        }

        if (expenseCategoryId != null){
            expenses = expenses
                    .stream()
                    .filter(e -> Objects.equals(e.getExpenseCategory().getId(), expenseCategoryId))
                    .toList();
        }


        return expenses
                .stream()
                .map(ExpenseResponse::new)
                .toList();
    }

    @Override
    public void deleteExpense(Long id, Boolean hard_delete) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()){
            if (hard_delete){
                expenseRepository.delete(expense.get());
            }else {
                expense.get().setDeletedAt(LocalDateTime.now());
                expense.get().setDeletedBy(69);
                expenseRepository.save(expense.get());
            }
        }else
            throw new ExpenseNotFoundException("Expense Not Found");

    }

    @Override
    public void update(Long id, ExpenseResource expenseResource) {
        Optional<Expense> expense = expenseRepository.findById(id);

        if (expense.isPresent()){
            expense.get().setAmount(expenseResource.amount() == null? expense.get().getAmount(): expenseResource.amount());
            expense.get().setDescription(expenseResource.description() == null? expense.get().getDescription(): expenseResource.description());
            expense.get().setDateExpense(expenseResource.expenseDate() == null? expense.get().getDateExpense(): expenseResource.expenseDate());
            if (expenseResource.expenseCategoryId() != null){
                Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findExpenseCategoryById(expenseResource.expenseCategoryId());
                expenseCategory.ifPresent(category -> expense.get().setExpenseCategory(category));
            }
            expense.get().setUpdatedAt(LocalDateTime.now());
            expense.get().setUpdatedBy(69);
            expenseRepository.save(expense.get());
        }else {
            throw new ExpenseNotFoundException("Expense not found");
        }
    }

    @Override
    public void save(ExpenseResource expenseResource) {
        Optional<ExpenseCategory> category = expenseCategoryRepository.findExpenseCategoryById(expenseResource.expenseCategoryId());
        if (category.isEmpty()){
            throw new ExpenseCategoryNotFoundException("Expense Category not found");
        }

        expenseRepository.save(
            new Expense(
                    expenseResource.amount(),
                    expenseResource.description(),
                    expenseResource.expenseDate(),
                    category.get()
            )
        );
    }

}
