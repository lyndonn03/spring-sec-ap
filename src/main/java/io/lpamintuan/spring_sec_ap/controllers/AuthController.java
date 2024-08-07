package io.lpamintuan.spring_sec_ap.controllers;

import org.springframework.web.bind.annotation.RestController;

import io.lpamintuan.spring_sec_ap.representations.ResponseAuth;
import io.lpamintuan.spring_sec_ap.representations.dto.UserAccountDetailsDTO;
import io.lpamintuan.spring_sec_ap.services.AuthService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(path = "/account")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAuth> login(@Valid @RequestBody UserAccountDetailsDTO userAccountDetails) {
        ResponseAuth authCreds = authService.login(userAccountDetails);
        return new ResponseEntity<>(authCreds, HttpStatus.OK);
    }
    
    
}
