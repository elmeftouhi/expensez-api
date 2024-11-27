package com.elmeftouhi.expensez.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseBuilder {

    public static BigDecimal amount = BigDecimal.valueOf(13.96);
    public static String description = "Default expense description";
    public static LocalDate dateExpense = LocalDate.now();

    public ExpenseBuilder(){}

    public static Expense anExpense(){
        return new Expense(
                amount,
                description,
                dateExpense,
                null
        );
    }

}
