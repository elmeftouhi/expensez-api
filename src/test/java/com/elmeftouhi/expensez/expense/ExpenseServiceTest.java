package com.elmeftouhi.expensez.expense;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.elmeftouhi.expensez.expense.ExpenseBuilder.anExpense;
import static org.mockito.Mockito.when;

class ExpenseServiceTest {

    @MockBean
    ExpenseRepository expenseRepository;

    @MockBean
    ExpenseService expenseService;

    @Test
    void getAll() {
        when(expenseRepository.findAll()).thenReturn(List.of(anExpense()));

    }
}