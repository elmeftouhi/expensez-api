package com.elmeftouhi.expensez.expensetag;

import com.elmeftouhi.expensez.expense.Expense;

import java.util.Set;

public record TagResource(
        Long id,
        String tag,
        String color,
        Set<Expense> expenses
) {
}
