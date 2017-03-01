package com.core.repository.impl;

import com.core.model.Account;
import com.core.repository.AccountRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/
@Repository
public class AccountRepoImpl implements AccountRepo {
    @PersistenceContext
    EntityManager em;
    public Account findAccountByName(String name) {
        Query query = em.createQuery("SELECT a FROM Account a WHERE a.name=?1");
        List<Account> resultList = query.setParameter(1, name).getResultList();
        if (!resultList.isEmpty())
            return resultList.get(0);
        else return null;
    }

    public Account findAccountById(Long accId) {
        return em.find(Account.class, accId);
    }

    public Account createOrUpdateAccount(Account acc) {
        if (acc.getId() == null) {
            em.persist(acc);
            return acc;
        }
        else{
            return em.merge(acc);
        }
    }

    public List<Account> findAllAccounts() {
        Query query = em.createQuery("SELECT a FROM Account a");
        List<Account> resultList = query.getResultList();
        return resultList;
    }


}
