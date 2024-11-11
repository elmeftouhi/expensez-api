package com.elmeftouhi.expensez.expense;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends ListCrudRepository<Expense, Long> {
    List<Expense> findAllByDeletedAtIsNull();

    List<Expense> findAllByDeletedAtIsNotNull();

    List<Expense> findAllByDeletedAtIsNullOrderByDateExpenseDesc();
}
