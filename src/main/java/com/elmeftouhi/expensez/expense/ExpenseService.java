package com.elmeftouhi.expensez.expense;

import java.util.List;

public interface ExpenseService {

    List<ExpenseResponse> getAll(Boolean includeDeleted, Boolean onlyDeleted, String description, Long expenseCategoryId, String from, String to);

    void deleteExpense(Long id, Boolean hardDelete);

    void update(Long id, ExpenseResource expenseResource);

    void save(ExpenseResource expenseResource);

    ReportResponse getTotalByMonthForAYear(String from, String to, Long expenseCategoryId);

    void toggleTag(Long expenseId, Long tagId);
}
