package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends ListCrudRepository<Expense, Long> {

    List<Expense> findAllByDeletedAtIsNotNullOrderByDateExpenseDesc();

    List<Expense> findAllByDeletedAtIsNullOrderByDateExpenseDesc();

    List<Expense> findAllByOrderByDateExpenseDesc();

    List<Expense> findByExpenseCategory(ExpenseCategory expenseCategory);
}
