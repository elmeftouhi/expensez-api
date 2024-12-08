package com.elmeftouhi.expensez.expensetag;

import com.elmeftouhi.expensez.expense.Expense;

import java.util.Set;

public record TagResponse(
        Long id,
        String tag,
        String color,
        Set<Expense> expenses
) {
    public TagResponse(Tag tag){
        this(
                tag.getId(),
                tag.getTagName(),
                tag.getColor(),
                null
        );
    }
}
