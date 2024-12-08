package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.core.jpa.BasicEntity;
import com.elmeftouhi.expensez.expensecategory.ExpenseCategory;
import com.elmeftouhi.expensez.expensetag.Tag;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "expenses")
public class Expense extends BasicEntity {

    private BigDecimal amount;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private ExpenseCategory expenseCategory;

    private LocalDate dateExpense;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private long deletedBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tags_of_expense",
            joinColumns = { @JoinColumn(name = "expense_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id") }
    )
    @JsonManagedReference
    private Set<Tag> tags = new HashSet<>();

    public Expense() {
    }

    public Expense(BigDecimal amount, String description, LocalDate dateExpense, ExpenseCategory expenseCategory){
        this.amount = amount;
        this.description = description;
        this.dateExpense = dateExpense;
        this.expenseCategory = expenseCategory;
    }

    public Expense(BigDecimal amount, String description, ExpenseCategory expenseCategory, LocalDate dateExpense, LocalDateTime deletedAt, long deletedBy) {
        this.amount = amount;
        this.description = description;
        this.expenseCategory = expenseCategory;
        this.dateExpense = dateExpense;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
