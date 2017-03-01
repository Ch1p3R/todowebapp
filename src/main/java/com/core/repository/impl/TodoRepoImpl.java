package com.core.repository.impl;

import com.core.model.Todo;
import com.core.repository.TodoRepo;
import com.core.service.exception.AccountDoesNotFindException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/
@Repository
public class TodoRepoImpl implements TodoRepo{
    @PersistenceContext
    private EntityManager em;

    public List<Todo> findAllForAccount(Long accId) {
        Query query = em.createQuery("SELECT t FROM Todo t WHERE t.account.id=?1");
        return query.setParameter(1, accId).getResultList();

    }

    public Todo createTodo(Todo todo) {
        em.persist(todo);
        return todo;
    }

    public Todo deleteTodo(Long id) {
        Todo todo = em.find(Todo.class, id);
        if (Objects.nonNull(todo)){
            em.remove(todo);
            return todo;
        }
        return todo;

    }

    public Todo updateTodo(Todo todo) {
        return em.merge(todo);
    }

    @Override
    public Todo findTodo(Long id) {
        return em.find(Todo.class, id);
    }
}
