package com.elmeftouhi.expensez.expense;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseTagRepository extends ListCrudRepository<ExpenseTag, Long> {
}
