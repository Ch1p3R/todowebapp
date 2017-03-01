package com.rest.mvc;

import com.core.model.Todo;
import com.core.service.TodoService;
import com.core.service.exception.TodoNotFountException;
import com.rest.resources.TodoResource;
import com.rest.resources.assembler.TodoResurceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.util.List;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/
@RequestMapping("/accounts/{accId}/todos")
public class TodoController {
    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TodoResource>> getAllTodos(@PathVariable Long accId){
        List<Todo> todos = todoService.findAllForAccount(accId);

        List<TodoResource> listTodoRes = new TodoResurceAssembler().toResources(todos);
        return new ResponseEntity<>(listTodoRes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TodoResource> createTodo(
            @PathVariable Long accId, @RequestBody TodoResource inputRes){

        Todo todo = inputRes.toTodo();
        Todo created = null;
        try {
            created = todoService.createTodo(accId, todo);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TodoResource res = new TodoResurceAssembler().toResource(created);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(res.getLink("self").getHref()));
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{todoId}")
    public ResponseEntity<TodoResource> getTodo(@PathVariable Long accId,
                                                @PathVariable Long todoId){

        Todo todo = null;
        try {
            todo = todoService.findTodo(todoId);
        } catch (TodoNotFountException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>
                (new TodoResurceAssembler().toResource(todo), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, name = "/{todoId}")
    public ResponseEntity<TodoResource> updateTodo(@RequestBody TodoResource inputRes){
        Todo todo = inputRes.toTodo();
        try {
            todoService.updateTodo(todo);
        } catch (TodoNotFountException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TodoResource todoRes = new TodoResurceAssembler().toResource(todo);
        return new ResponseEntity<>(todoRes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{todoId}")
    public ResponseEntity<TodoResource> deleteTodo(@PathVariable Long todoId){
        Todo todo = null;
        try {
            todo = todoService.deleteTodo(todoId);
        } catch (TodoNotFountException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TodoResource todoRes = new TodoResurceAssembler().toResource(todo);
        return new ResponseEntity<>(todoRes, HttpStatus.OK);
    }

}
