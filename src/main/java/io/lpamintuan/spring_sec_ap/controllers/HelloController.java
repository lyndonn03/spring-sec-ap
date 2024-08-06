package io.lpamintuan.spring_sec_ap.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class HelloController {

    @GetMapping()
    public ResponseEntity<Map<String, String>> hello() {
        Map<String, String> message = new HashMap<>();
        message.put("hello", "user");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    
}
