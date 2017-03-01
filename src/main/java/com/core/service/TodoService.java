package com.core.service;

import com.core.model.Account;
import com.core.model.Todo;

import java.util.List;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public interface TodoService {

    List<Todo> findAllForAccount(Long accId);
    Todo createTodo(Long accId, Todo todo);
    Todo deleteTodo(Long todoId);
    Todo updateTodo(Todo todo);
    Todo findTodo(Long id);
}
