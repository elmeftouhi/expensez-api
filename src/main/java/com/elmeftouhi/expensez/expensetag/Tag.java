package com.elmeftouhi.expensez.expensetag;

import jakarta.persistence.*;

@Entity
@Table(name = "expense_tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="tag")
    private String tag;

    @Column(name = "color")
    private String color;

    public Tag() {
    }

    public Tag(Long id, String tag, String color) {
        this.id = id;
        this.tag = tag;
        this.color = color;
    }

    public Tag(String tag, String color) {
        this.tag = tag;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
