package com.elmeftouhi.expensez.expensecategory;


public interface ExpenseCategoryService {

    public void updateLevel(Long id, ExpenseCategoryLevelDirection direction);

    void update(Long id, ExpenseCategoryResource expenseCategoryResource);

    void delete(Long id);
}
