package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Query(value = """
    WITH months AS (
        SELECT 1 AS month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL
        SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL
        SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL
        SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12
    ),
    filtered_expenses AS (
        SELECT 
            amount, 
            EXTRACT(MONTH FROM date_expense) AS expense_month,
            EXTRACT(YEAR FROM date_expense) AS expense_year
        FROM expenses
        WHERE deleted_at IS NULL
    ),
    monthly_sums AS (
        SELECT 
            m.month,
            COALESCE(SUM(fe.amount), 0) AS total_amount
        FROM months m
        LEFT JOIN filtered_expenses fe
        ON m.month = fe.expense_month AND fe.expense_year = CAST(:year AS NUMERIC)
        GROUP BY m.month
    )
    SELECT 
        month,
        total_amount
    FROM monthly_sums
    ORDER BY month;
    """, nativeQuery = true)
    List<Object[]> getMonthlyExpensesByYear(@Param("year") String year);
}
