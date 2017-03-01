package com.core.service;

import com.core.model.Account;

import java.util.List;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public interface AccountService {
    Account findAccountByName(String name);
    Account findAccountById(Long accId);
    Account createAccount(Account acc);
    Account updateAccount(Account acc);
    List<Account> findAllAccounts();
}
