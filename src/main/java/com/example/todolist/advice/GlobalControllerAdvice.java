package com.example.todolist.advice;

import com.example.todolist.NotFoundException.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ErrorResponse NotFoundExceptionHandling(NotFoundException todoNotFoundException){
        return new ErrorResponse(todoNotFoundException.getMessage());
    }

}