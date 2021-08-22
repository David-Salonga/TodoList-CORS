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
        todo.setDone(false);
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Integer id, Todo todo) {
        Todo todoUpdate = getById(id);
        if(todo.getText() != null){
            todoUpdate.setText(todo.getText());
        } else {
            todoUpdate.setDone(todo.getDone());
        }
        return todoRepository.save(todoUpdate);
    }

    public void deleteTodo(Integer id){
        todoRepository.delete(getById(id)); 
    }

}
