package com.elmeftouhi.expensez.expense;

import java.util.List;

public interface ExpenseService {

    List<ExpenseResponse> getAll(Boolean include_deleted, Boolean only_deleted, String description, Long expenseCategoryId, String from, String to);

    void deleteExpense(Long id, Boolean hard_delete);

    void update(Long id, ExpenseResource expenseResource);

    void save(ExpenseResource expenseResource);

    ReportResponse getTotalByMonthForAYear(String from, String to, Long expenseCategoryId);

}
