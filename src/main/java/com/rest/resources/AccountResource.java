package com.rest.resources;

import com.core.model.Account;
import org.springframework.hateoas.ResourceSupport;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public class AccountResource extends ResourceSupport{

    private Long accId;
    private String name;
    private String password;

    public Account toAccount(){
        Account acc = new Account();
        acc.setId(getAccId());
        acc.setName(getName());
        acc.setPassword(getPassword());
        return acc;
    }

    public Long getAccId() {
        return accId;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
