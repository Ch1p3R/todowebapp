package com.core.service.impl;

import com.core.model.Account;
import com.core.repository.AccountRepo;
import com.core.service.AccountService;
import com.core.service.exception.AccountDoesNotFindException;
import com.core.service.exception.AccountExistException;
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
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepo accountRepo;
    @Override
    public Account findAccountByName(String name) {
        return accountRepo.findAccountByName(name);
    }

    @Override
    public Account findAccountById(Long accId) {
        Account accountById = accountRepo.findAccountById(accId);
        if (Objects.isNull(accountById)){
            throw new AccountDoesNotFindException();
        }
        return accountById;
    }

    @Override
    public Account createAccount(Account acc) {
        Account accByName = accountRepo.findAccountByName(acc.getName());
        if (Objects.nonNull(accByName)){
            throw new AccountExistException();
        }
        return accountRepo.createOrUpdateAccount(acc);
    }
    @Override
    public Account updateAccount(Account acc) {
        Account updatedAccount = accountRepo.createOrUpdateAccount(acc);
        if (Objects.isNull(updatedAccount)){
            throw new AccountDoesNotFindException();
        }
        return updatedAccount;
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepo.findAllAccounts();
    }
}
