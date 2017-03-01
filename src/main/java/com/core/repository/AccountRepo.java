package com.core.repository;

import com.core.model.Account;

import java.util.List;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public interface AccountRepo {
    Account findAccountByName(String name);
    Account findAccountById(Long accId);
    Account createOrUpdateAccount(Account acc);
    List<Account> findAllAccounts();

}
