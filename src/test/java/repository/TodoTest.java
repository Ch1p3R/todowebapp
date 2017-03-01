package repository;

import com.core.model.Account;

import com.core.model.Todo;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/
@Rollback(false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/applicationContext.xml")
@SqlGroup({
        @Sql(scripts = "classpath:/mySql/initDB.sql"),
        @Sql(scripts = "classpath:/mySql/populateDB.sql")
}
)
public class TodoTest {
    @Autowired
    private TodoService todoService;

    private Account account;

    @Before
    @Transactional
    public void init(){
        account = new Account();
        account.setName("Kira");

        List<Todo> todos = new ArrayList<Todo>(){{
            add(new Todo("That\'s created by Kira"));
            add(new Todo("Olol la"));
        }};
        for(Todo t : todos){
            t.setAccount(account);
        }
    }
    @Test
    public void createTodo(){
        Todo todo = new Todo("Test todo");
        todoService.createTodo(3L, todo);
        List<Todo> todoList = todoService.findAllForAccount(3L);
        assertThat(todoList, hasItem(todo));
    }

}
