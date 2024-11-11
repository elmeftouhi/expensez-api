package com.elmeftouhi.expensez.expensecategory;

import java.time.LocalDateTime;

public class ExpenseCategoryResponse {
    private Long id;
    private String name;
    private LocalDateTime created_at;
    private Long created_by;
    private LocalDateTime updated_at;
    private Long updated_by;
    private Integer level;
    private ExpenseCategoryStatus status;

    public ExpenseCategoryResponse(ExpenseCategory expenseCategory){
        this.id = expenseCategory.getId();
        this.name = expenseCategory.getName();
        this.created_at = expenseCategory.getCreatedAt();
        this.created_by = expenseCategory.getCreated_by();
        this.updated_by = expenseCategory.getUpdated_by();
        this.updated_at = expenseCategory.getUpdated_at();
        this.level = expenseCategory.getLevel();
        this.status = expenseCategory.getExpenseCategoryStatus();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpenseCategoryStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseCategoryStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public Long getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(Long updated_by) {
        this.updated_by = updated_by;
    }
}