package com.elmeftouhi.expensez.expensecategory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseCategoryRepository extends ListCrudRepository<ExpenseCategory, Long> {

    @Query(" SELECT e FROM ExpenseCategory e WHERE e.id = :id AND e.deletedAt IS NULL ")
    Optional<ExpenseCategory> findExpenseCategoryById(@Param("id") Long id);

    @Query(" SELECT expenseCategory FROM ExpenseCategory expenseCategory ORDER BY expenseCategory.level ")
    List<ExpenseCategory> findAllOrderByLevel();

    List<ExpenseCategory> findAllByDeletedAtIsNullOrderByCreatedAtDesc();

    List<ExpenseCategory> findAllByDeletedAtIsNotNullOrderByLevel();

    List<ExpenseCategory> findAllByDeletedAtIsNullOrderByLevel();
}
