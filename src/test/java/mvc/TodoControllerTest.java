package mvc;

import com.core.model.Account;
import com.core.model.Todo;
import com.core.service.TodoService;
import com.rest.mvc.TodoController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MADE WITH LOVE
 * ♥ February 2017 ♥
 **/

public class TodoControllerTest {
    @InjectMocks
    private TodoController todoController;
    @Mock
    private TodoService todoService;

    private MockMvc mvc;
    private ArgumentCaptor<Todo> todoArgumentCaptor;

    private Account testAcc;
    private List<Todo> todoList;
    private Todo todo;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(todoController).build();
        todoArgumentCaptor = ArgumentCaptor.forClass(Todo.class);

        testAcc = new Account();
        testAcc.setId(1L);
        testAcc.setName("Adjurab");
        testAcc.setPassword("111111");

        todoList = new ArrayList<Todo>(){{
            add(new Todo("test todo1"));
            add(new Todo("test todo2"));
        }};
        Long id = 1L;
        for(Todo t : todoList){
            t.setId(id++);
            t.setAccount(testAcc);
        }
        todo = new Todo("Test todo");
        todo.setId(1L);
        todo.setAccount(testAcc);
    }

    @Test
    public void getAllTodoForAcc() throws Exception {

        when(todoService.findAllForAccount(anyLong())).thenReturn(todoList);

        mvc.perform(get("/accounts/1/todos"))
                .andDo(print())
                .andExpect(jsonPath("$.[*].description",
                        hasItems(is("test todo1"), is("test todo2"))))
                .andExpect(jsonPath("$.[*].links[*].href",
                        hasItems(endsWith("accounts/1/todos/1"), endsWith("accounts/1/todos/2"))))
                .andExpect(jsonPath("$.[*].links[*].href",
                        hasItem(endsWith("/accounts/1"))))
                .andExpect(jsonPath("$.[*].links[*].rel", hasItem(is("owner"))))
                .andExpect(status().isOk());
    }

    @Test
    public void createTodo() throws Exception{
        when(todoService.createTodo(anyLong(), Matchers.any(Todo.class)))
                .thenReturn(todo);
        mvc.perform(post("/accounts/1/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"description\":\"Test todo\"}"))
                .andDo(print())
                .andExpect(jsonPath("$.description", is(todo.getDescription())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("accounts/1/todos/1"))))
                .andExpect(status().isCreated());
    }

}
