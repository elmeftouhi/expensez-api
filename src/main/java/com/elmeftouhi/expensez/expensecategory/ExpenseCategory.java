package com.elmeftouhi.expensez.expensecategory;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "expense_categories")
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer level = 0;

    private ExpenseCategoryStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    private long created_by;

    private LocalDateTime updated_at = LocalDateTime.now();

    private long updated_by;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private long deletedBy;

    public ExpenseCategory() {
    }

    public ExpenseCategory(String name, Integer level, ExpenseCategoryStatus status, LocalDateTime createdAt, long created_by, LocalDateTime updated_at, long updated_by, LocalDateTime deletedAt, Long deletedBy) {
        this.name = name;
        this.createdAt = createdAt;
        this.created_by = created_by;
        this.updated_at = updated_at;
        this.updated_by = updated_by;
        this.level = level;
        this.status = status;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
    }

    public ExpenseCategory(String name, ExpenseCategoryStatus status){
        this.name = name;
        this.status = status;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public ExpenseCategoryStatus getExpenseCategoryStatus() {
        return status;
    }

    public void setExpenseCategoryStatus(ExpenseCategoryStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(long created_by) {
        this.created_by = created_by;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public long getUpdated_by() {
        return updated_by;
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

    public void setUpdated_by(long updated_by) {
        this.updated_by = updated_by;
    }

    public void incrementLevel(){
        this.level++;
    }

    public void decrementLevel(){
        if (this.level == 1)
            this.level = 1;
        else
            this.level--;
    }
}
