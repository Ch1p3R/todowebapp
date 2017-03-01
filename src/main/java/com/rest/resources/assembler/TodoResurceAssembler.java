package com.rest.resources.assembler;

import com.core.model.Todo;
import com.rest.mvc.AccountController;
import com.rest.mvc.TodoController;
import com.rest.resources.TodoResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public class TodoResurceAssembler extends ResourceAssemblerSupport<Todo, TodoResource> {

    public TodoResurceAssembler() {
        super(TodoController.class, TodoResource.class);
    }

    @Override
    public TodoResource toResource(Todo entity) {
        TodoResource res = new TodoResource();
        res.setId(entity.getId());
        res.setDescription(entity.getDescription());

        System.out.println(entity);
        res.add(linkTo(methodOn(TodoController.class)
                .getTodo(entity.getAccount().getId(), entity.getId())).withSelfRel());
        res.add(linkTo(methodOn(AccountController.class)
                .getAccount(entity.getAccount().getId())).withRel("owner"));
        return res;
    }
}
