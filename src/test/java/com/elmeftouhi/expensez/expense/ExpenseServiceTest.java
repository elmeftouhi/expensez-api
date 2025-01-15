package com.elmeftouhi.expensez.expense;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.elmeftouhi.expensez.expense.ExpenseBuilder.anExpense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExpenseServiceTest {

    @Mock
    ExpenseRepository expenseRepository;

    private final LocalDate DATE_FROM = LocalDate.now().minusMonths(1);
    private final LocalDate DATE_TO = LocalDate.now();

    @InjectMocks
    ExpenseServiceImpl expenseService;

    @Test
    void getAll() {
        when(expenseRepository.findAllBetweenDates(DATE_FROM, DATE_TO)).thenReturn(List.of(anExpense()));

        List<ExpenseResponse> expenseResponses = expenseService.getAll(
                false,
                false,
                null,
                null,
                DATE_FROM.toString(),
                DATE_TO.toString()
        );

        assertThat(expenseResponses).isNotNull().hasSize(1);

    }
}