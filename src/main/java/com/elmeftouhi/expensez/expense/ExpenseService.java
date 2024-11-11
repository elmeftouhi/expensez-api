package com.elmeftouhi.expensez.expense;

public interface ExpenseService {
    void deleteExpense(Long id);

    void update(Long id, ExpenseResource expenseResource);
}
