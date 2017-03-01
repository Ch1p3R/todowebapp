package com.rest.resources;

import com.core.model.Todo;
import org.springframework.hateoas.ResourceSupport;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public class TodoResource extends ResourceSupport {

    private Long todoId;
    private String description;

    public Todo toTodo(){
        Todo todo = new Todo();
        todo.setId(getTodoId());
        todo.setDescription(getDescription());
        return todo;
    }

    public Long getTodoId() {
        return todoId;
    }

    public void setId(Long id) {
        this.todoId = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
