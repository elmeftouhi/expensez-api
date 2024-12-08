package com.elmeftouhi.expensez.expensetag;

import com.elmeftouhi.expensez.expense.Expense;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="tag")
    private String tagName;

    @Column(name = "color")
    private String color;

    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    Set<Expense> expenses = new HashSet<>();

    public Tag() {
    }

    public Tag(Long id, String tagName, String color) {
        this.id = id;
        this.tagName = tagName;
        this.color = color;
    }

    public Tag(String tagName, String color) {
        this.tagName = tagName;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }
}
