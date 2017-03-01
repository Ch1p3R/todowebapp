package com.core.repository;

import com.core.model.Todo;

import java.util.List;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public interface TodoRepo {
    List<Todo> findAllForAccount(Long accId);
    Todo createTodo(Todo todo);
    Todo deleteTodo(Long id);
    Todo updateTodo(Todo todo);
    Todo findTodo(Long id);
}
