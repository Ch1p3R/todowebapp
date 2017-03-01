package com.rest.resources.assembler;

import com.core.model.Account;
import com.rest.mvc.AccountController;
import com.rest.resources.AccountResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public class AccountResourceAssembler extends ResourceAssemblerSupport<Account, AccountResource> {
    public AccountResourceAssembler() {
        super(AccountController.class, AccountResource.class);
    }

    @Override
    public AccountResource toResource(Account account) {
        AccountResource res = new AccountResource();
        res.setAccId(account.getId());
        res.setName(account.getName());
        res.setPassword(account.getPassword());
        res.add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());
        res.add(linkTo(methodOn(AccountController.class)
                .getAccount(account.getId())).slash("todos").withRel("todos"));
        return res;
    }
}
