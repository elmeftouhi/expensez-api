package com.elmeftouhi.expensez.expense;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
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
}
