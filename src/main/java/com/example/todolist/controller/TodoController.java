package com.example.todolist.controller;

import com.example.todolist.model.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getTodo(){
        return todoService.getAllTodos();
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo){
        return todoService.addTodo(todo);
    }


    @PutMapping({"/{id}"})
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo ){
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping({"/{id}"})
    public void deleteTodo(@PathVariable Integer id){
        todoService.deleteTodo(id);
    }



}
