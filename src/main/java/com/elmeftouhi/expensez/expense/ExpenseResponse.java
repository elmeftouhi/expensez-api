package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import com.elmeftouhi.expensez.expensetag.TagResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ExpenseResponse(
        Long id,
        BigDecimal amount,
        String description,
        ExpenseCategory expenseCategory,
        LocalDate dateExpense,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updatedAt,
        Long updatedBy,
        LocalDateTime deletedAt,
        Long deletedBy,
        Set<TagResponse> tags
) {

    public ExpenseResponse(Expense expense) {
        this(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getExpenseCategory(),
                expense.getDateExpense(),
                expense.getCreatedAt(),
                expense.getCreatedBy(),
                expense.getUpdatedAt(),
                expense.getUpdatedBy(),
                expense.getDeletedAt(),
                expense.getDeletedBy(),
                expense.getTags().stream().map(TagResponse::new).collect(Collectors.toSet())
        );
    }

}
