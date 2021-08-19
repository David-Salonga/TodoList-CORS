package com.example.todolist.service;

import com.example.todolist.NotFoundException.NotFoundException;
import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo getById(Integer id){
        return todoRepository.findById(id).orElseThrow(() -> new NotFoundException("Todo not found."));
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

//
//    public Todo updateTodo(Integer id, Todo todo) {
//        Todo todoId = getById(id);
//        return todoRepository.save(updateToTrue(todoId,todo));
//    }
//
//    private Todo updateToTrue(Todo todo, Todo TodoToBeUpdated){
//
//    }
}
