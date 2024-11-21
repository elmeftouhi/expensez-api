package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends ListCrudRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e WHERE e.deletedAt IS NOT NULL AND e.dateExpense BETWEEN :from AND :to ORDER BY e.dateExpense Desc")
    List<Expense> findOnlyDeletedBetweenDates(LocalDate from, LocalDate to);

    @Query("SELECT e FROM Expense e WHERE e.deletedAt IS NULL AND e.dateExpense BETWEEN :from AND :to ORDER BY e.dateExpense Desc")
    List<Expense> findAllBetweenDates(LocalDate from, LocalDate to);

    @Query("SELECT e FROM Expense e WHERE e.dateExpense BETWEEN :from AND :to ORDER BY e.dateExpense Desc")
    List<Expense> findAllByOrderByDateExpenseDesc(LocalDate from, LocalDate to);

    List<Expense> findByExpenseCategory(ExpenseCategory expenseCategory);
}
