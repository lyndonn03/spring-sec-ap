package io.lpamintuan.spring_sec_ap.representations;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class ResponseError<T> {

    private int code;

    private String message;

    private LocalDateTime timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T errors;

    public ResponseError(T errors, HttpStatus status, String message) {
        this.errors = errors;
        this.code = status.value();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ResponseError(HttpStatus status) {
        this(null, status, status.getReasonPhrase());
    }
    
}
