package com.core.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/
@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;
    public Todo() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Todo(String discription) {
        this.description = discription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id) &&
                Objects.equals(description, todo.description) &&
                Objects.equals(account, todo.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
