package com.example.todolist.integration;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @BeforeEach
    void tearDown() {
        todoRepository.deleteAll();
    }


    @Test
    void should_return_all_todos_when_call_get_todos_api() throws Exception {
        //given
        final Todo todo = new Todo("first to do item", false);
        todoRepository.save(todo);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("first to do item"))
                .andExpect(jsonPath("$[0].done").value(false));
    }

    @Test
    void should_return_todo_when_get_given_id() throws Exception{
        //given
        final Todo todo = new Todo( "First Todo", false);
        final Todo saveTodoToDB =  todoRepository.save(todo);
        int id = saveTodoToDB.getId();
                
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("First Todo"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_create_todo_when_call_add_todo() throws Exception {
        //given
        String todo = "{\n" +
                "    \"text\": \"new to do item\",\n" +
                "    \"done\": false\n" +
                "}";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON).content(todo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("new to do item"))
                .andExpect(jsonPath("$.done").value(false));

    }

    @Test
    void should_update_todo_when_call_update_todo() throws Exception {
        //given
        final Todo todo = new Todo("first new to do item", false);
        todoRepository.save(todo);
        Integer todoId = todo.getId();

        String todoUpdate = "{\n" +
                "    \"done\": true\n" +
                "}";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/todos/{todoId}", todoId)
                .contentType(MediaType.APPLICATION_JSON).content(todoUpdate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_delete_todo_when_call_delete_todo() throws Exception {
        //given
        final Todo firstTodo = new Todo("new first to do item", false);
        final Todo secondTodo = new Todo("new second to do item", true);
        todoRepository.saveAll(Lists.list(firstTodo, secondTodo));
        Integer firstTodoId = firstTodo.getId();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{firstTodoId}", firstTodoId))
                .andExpect(status().isOk());
    }
    
}
