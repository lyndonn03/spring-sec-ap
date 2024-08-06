package io.lpamintuan.spring_sec_ap.configs.authErrors;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.lpamintuan.spring_sec_ap.representations.ResponseError;

@ControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ResponseError<String>> handleUsernameNotFound(AuthenticationException ue) {
        ResponseError<String> errorMessage =  new ResponseError<>(null, HttpStatus.BAD_REQUEST, ue.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    
}
