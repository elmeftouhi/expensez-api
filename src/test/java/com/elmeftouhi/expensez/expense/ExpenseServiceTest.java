package com.elmeftouhi.expensez.expense;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static com.elmeftouhi.expensez.expense.ExpenseBuilder.anExpense;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExpenseServiceTest {

    @MockBean
    ExpenseRepository expenseRepository;

    @MockBean
    ExpenseServiceImpl expenseService;

    @Test
    void getAll() {
        when(expenseRepository.findAll()).thenReturn(List.of(anExpense()));

        List<ExpenseResponse> expenseResponses = expenseService.getAll(
                false,
                false,
                null,
                null,
                LocalDate.now().toString(),
                ""
        );

        assertThat(expenseResponses).isNotNull().hasSize(1);

    }
}