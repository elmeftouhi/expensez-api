package com.elmeftouhi.expensez.expensecategory;


import java.util.Optional;

public interface ExpenseCategoryService {

    public void updateLevel(Long id, ExpenseCategoryLevelDirection direction);

    void update(Long id, ExpenseCategoryResource expenseCategoryResource);

    void delete(Long id, Boolean hard_delete);

    ExpenseCategory findExpenseCategoryById(Long id);

}
