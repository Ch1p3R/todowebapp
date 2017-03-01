package repository;

import com.core.model.Account;
import com.core.model.Todo;
import com.core.service.AccountService;
import com.core.service.TodoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.assertEquals;


/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/
@Rollback(false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext.xml")
@SqlGroup({
        @Sql(scripts = "classpath:/mySql/initDB.sql"),
        @Sql(scripts = "classpath:/mySql/populateDB.sql")}
)
public class AccountTest {
    @Autowired
    private AccountService accountService;

    private Account account;

    @Before
    @Transactional
    public void init(){
        account = new Account();
        account.setName("Kira");
    }

    @Test
    public void findAcc(){
        Account found = accountService.findAccountByName(account.getName());
        assertEquals(account.getName(), found.getName());
    }

    @Test
    public void updateAccount(){
        Account kiraAcc = accountService.findAccountByName("Kira");
        kiraAcc.setName("Kristina");
        Account updatedAcc = accountService.updateAccount(kiraAcc);
        assertEquals(updatedAcc, accountService.findAccountByName("Kristina"));
    }

}
