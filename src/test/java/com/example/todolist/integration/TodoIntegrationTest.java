package com.example.todolist.integration;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
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



    
    
}
