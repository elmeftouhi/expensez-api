package com.elmeftouhi.expensez.expense;

import jakarta.persistence.*;

@Entity
@Table(name = "tags_of_expense")
public class ExpenseTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tag_id;

    private Long expense_id;

    public ExpenseTag() {
    }

    public ExpenseTag(Long id, Long tag_id, Long expense_id) {
        this.id = id;
        this.tag_id = tag_id;
        this.expense_id = expense_id;
    }

    public ExpenseTag(Long tag_id, Long expense_id) {
        this.tag_id = tag_id;
        this.expense_id = expense_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTag_id() {
        return tag_id;
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }

    public Long getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(Long expense_id) {
        this.expense_id = expense_id;
    }
}
