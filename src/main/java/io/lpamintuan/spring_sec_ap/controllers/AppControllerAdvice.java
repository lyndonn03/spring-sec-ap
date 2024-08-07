package io.lpamintuan.spring_sec_ap.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.lpamintuan.spring_sec_ap.representations.ResponseError;

@RestControllerAdvice
public class AppControllerAdvice {
    

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException me) {
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = me.getFieldErrors();
        for(FieldError fe : fieldErrors) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        return new ResponseError<>(errors, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }


}
