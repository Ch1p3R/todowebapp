package com.core.service.impl;

import com.core.model.Account;
import com.core.model.Todo;
import com.core.repository.AccountRepo;
import com.core.repository.TodoRepo;
import com.core.service.TodoService;
import com.core.service.exception.AccountDoesNotFindException;
import com.core.service.exception.TodoNotFountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/
@Service
@Transactional
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepo todoRepo;
    @Autowired
    private AccountRepo accountRepo;
    @Override
    public List<Todo> findAllForAccount(Long accId) {
        return todoRepo.findAllForAccount(accId);
    }

    @Override
    public Todo createTodo(Long accountId, Todo todo) {
        Account accountById = accountRepo.findAccountById(accountId);
        if (Objects.isNull(accountById)){
            throw new AccountDoesNotFindException();
        }
        todo.setAccount(accountById);
        return todoRepo.createTodo(todo);
    }

    @Override
    public Todo deleteTodo(Long todoId) {
        Todo todo = todoRepo.deleteTodo(todoId);
        if (Objects.isNull(todo)){
            throw new TodoNotFountException();
        }
        return todo;
    }

    @Override
    public Todo updateTodo(Todo todo) {
        Todo updatedTodo = todoRepo.updateTodo(todo);
        if (Objects.isNull(updatedTodo)){
            throw new TodoNotFountException();
        }
        return updatedTodo;
    }

    @Override
    public Todo findTodo(Long id) {
        Todo todo = todoRepo.findTodo(id);
        if (Objects.isNull(todo)){
            throw new TodoNotFountException();
        }
        return todo;
    }
}
