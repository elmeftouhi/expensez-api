package com.elmeftouhi.expensez.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResource(
        BigDecimal amount,
        String description,
        Long expenseCategoryId,
        LocalDate expenseDate
) {
}
