package com.rest.mvc;

import com.core.model.Account;
import com.core.service.AccountService;
import com.core.service.exception.AccountDoesNotFindException;
import com.core.service.exception.AccountExistException;
import com.rest.exception.ConflictException;
import com.rest.resources.AccountResource;
import com.rest.resources.assembler.AccountResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/
@Controller
@RequestMapping(value = "/accounts")
public class AccountController {

    private AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AccountResource>> getAccounts(
            @RequestParam(value = "name", required = false) String name){

        List<AccountResource> res = null;
        if (Objects.isNull(name)){
            List<Account> accounts = accountService.findAllAccounts();
            res = new AccountResourceAssembler().toResources(accounts);
        }else {
            Account acc = accountService.findAccountByName(name);
            if (Objects.isNull(acc)){
                res = new ArrayList<AccountResource>();
            }
            else{
                res = Arrays.asList(new AccountResourceAssembler().toResource(acc));
            }
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AccountResource> createAccount
            (@RequestBody AccountResource inputRes){
        Account acc = inputRes.toAccount();
        try {
            Account account = accountService.createAccount(acc);
            AccountResource res = new AccountResourceAssembler().toResource(account);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(res.getLink("self").getHref()));
            return new ResponseEntity<AccountResource>(res, headers, HttpStatus.CREATED);
        } catch (AccountExistException e) {
            throw new ConflictException(e);
        }
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/{accountId}")
    public ResponseEntity<AccountResource> updateAccount
            (@RequestBody AccountResource inputRes){

        Account acc = inputRes.toAccount();
        try {
            Account account = accountService.updateAccount(acc);
            AccountResource res = new AccountResourceAssembler().toResource(account);
            return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
        } catch (AccountDoesNotFindException e) {
            throw new ConflictException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{accountId}")
    public ResponseEntity<AccountResource> getAccount
            (@PathVariable Long accountId){
        try {
            Account accountById = accountService.findAccountById(accountId);
            AccountResource res = new AccountResourceAssembler().toResource(accountById);
            return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
        } catch (AccountDoesNotFindException e) {
            return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
        }
    }


}
