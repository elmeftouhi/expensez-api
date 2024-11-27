package com.elmeftouhi.expensez.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExpenseNotFoundException extends BaseException{
    public ExpenseNotFoundException(){
        super("Expense not found");
    }
    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
