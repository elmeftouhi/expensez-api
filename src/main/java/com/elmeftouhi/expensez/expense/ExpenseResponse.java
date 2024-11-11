package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExpenseResponse {
    private Long id;

    private BigDecimal amount;

    private String description;

    private ExpenseCategory expenseCategory;

    private LocalDate dateExpense;

    private LocalDateTime createdAt;

    private long createdBy;

    private LocalDateTime updatedAt;

    private long updatedBy;

    private LocalDateTime deletedAt;

    private long deletedBy;

    public ExpenseResponse(Expense expense) {
        this.id = expense.getId();
        this.amount = expense.getAmount();
        this.description = expense.getDescription();
        this.expenseCategory = expense.getExpenseCategory();
        this.dateExpense = expense.getDateExpense();
        this.createdAt = expense.getCreatedAt();
        this.createdBy = expense.getCreatedBy();
        this.updatedAt = expense.getUpdatedAt();
        this.updatedBy = expense.getUpdatedBy();
        this.deletedAt = expense.getDeletedAt();
        this.deletedBy = expense.getDeletedBy();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public LocalDate getDateExpense() {
        return dateExpense;
    }

    public void setDateExpense(LocalDate dateExpense) {
        this.dateExpense = dateExpense;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(long deletedBy) {
        this.deletedBy = deletedBy;
    }
}
