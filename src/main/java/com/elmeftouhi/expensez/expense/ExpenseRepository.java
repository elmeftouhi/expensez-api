package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends ListCrudRepository<Expense, Long> {
    List<Expense> findAllByDeletedAtIsNull();

    List<Expense> findAllByDeletedAtIsNotNull();

    List<Expense> findAllByDeletedAtIsNullOrderByDateExpenseDesc();

    List<Expense> findByExpenseCategory(ExpenseCategory expenseCategory);
}
