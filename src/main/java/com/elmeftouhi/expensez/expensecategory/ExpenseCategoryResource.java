package com.elmeftouhi.expensez.expensecategory;

public record ExpenseCategoryResource(
        String name,
        Integer level,
        ExpenseCategoryStatus status
) {
}
