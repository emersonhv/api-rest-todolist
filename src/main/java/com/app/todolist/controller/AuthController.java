package com.app.todolist.controller;

import com.app.todolist.model.dto.AuthDto;
import com.app.todolist.model.dto.LoginDto;
import com.app.todolist.model.dto.UserDto;
import com.app.todolist.service.AuthSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthSevice authSevice;

    private Map<String, String> response;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {
        try {
            AuthDto authResponse = authSevice.login(request);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception exception) {
            response = new HashMap<>();
            response.put("message", "Nombre de usuario o contraseña incorrecto!");
            response.put("token", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<?> register(@RequestBody UserDto request) {
        try {
            return new ResponseEntity<>(authSevice.register(request), HttpStatus.CREATED);
        } catch (Exception exception) {
            response = new HashMap<>();
            response.put("message", "Error al crear un nuevo usuario!");
            response.put("token", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
