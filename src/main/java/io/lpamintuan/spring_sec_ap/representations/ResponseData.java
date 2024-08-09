package io.lpamintuan.spring_sec_ap.representations;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ResponseData<T> {
    
    private T data;

    private int code;

    private String message;

    private LocalDateTime timestamp;

    public ResponseData(T data, HttpStatus status, String message) {
        this.data = data;
        this.code = status.value();
        this.message = (message == null) ? status.getReasonPhrase() : message;
        this.timestamp = LocalDateTime.now();
    }
    
}

