package mvc;

import com.core.model.Account;
import com.core.service.AccountService;
import com.core.service.exception.AccountExistException;
import com.rest.mvc.AccountController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public class AccountControllerTest {
    @Mock
    private AccountService accountService;
    @InjectMocks
    private AccountController accountController;
    ArgumentCaptor<Account> argumentCaptor;
    private MockMvc mvc;
    private Account account;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(accountController).build();
        argumentCaptor = ArgumentCaptor.forClass(Account.class);
        account = new Account();
        account.setId(1L);
        account.setName("Zurab");
        account.setPassword("111111");
    }

    @Test
    public void createAccount() throws Exception {
        when(accountService.createAccount(any(Account.class))).thenReturn(account);
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Zurab\", \"password\":\"111111\"}"))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(account.getName())))
                .andExpect(jsonPath("$.links[*].href",
                        hasItems(endsWith("/accounts/1"), endsWith("/accounts/1/todos"))))
                .andExpect(header().string("Location", endsWith("/accounts/1")))
                .andExpect(status().isCreated());
        verify(accountService).createAccount(argumentCaptor.capture());
    }
    @Test
    public void createExistedAccount() throws Exception{
        when(accountService.createAccount(any(Account.class))).thenThrow(new AccountExistException());
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Zurab\", \"password\":\"111111\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void getAccount() throws Exception {
        when(accountService.findAccountByName(account.getName())).thenReturn(account);
        mvc.perform(get("/accounts").param("name", "Zurab"))
                .andDo(print())
                .andExpect(jsonPath("$.[*].name", hasItem("Zurab")))
                .andExpect(jsonPath("$.[*].links[*].href",
                        hasItems(endsWith("/accounts/1"), endsWith("/accounts/1/todos"))))
                .andExpect(status().isOk());
    }


    @Test
    public void getAllAccounts() throws Exception{
        Account secondAcc = new Account();
        secondAcc.setName("Antonio");
        secondAcc.setPassword("qwerty");
        secondAcc.setId(2L);
        List<Account> accounts = new ArrayList<Account>(){{
           add(account);
           add(secondAcc);
        }};
        when(accountService.findAllAccounts()).thenReturn(accounts);

        mvc.perform(get("/accounts"))
                .andDo(print())
                .andExpect(jsonPath("$.[*].name",
                        hasItems(endsWith("Antonio"), endsWith("Zurab"))))
                .andExpect(status().isOk());
    }

}
