package com.elmeftouhi.expensez.expensecategory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseCategoryRepository extends ListCrudRepository<ExpenseCategory, Long> {
    @Query("""
            SELECT expenseCategory FROM ExpenseCategory expenseCategory ORDER BY expenseCategory.level
            """)
    public List<ExpenseCategory> findAllOrderByLevel();

    public List<ExpenseCategory> findAllByOrderByCreatedAtDesc();
}
