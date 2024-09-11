package com.app.todolist.controller;

import com.app.todolist.model.dto.AuthDto;
import com.app.todolist.model.dto.UserDto;
import com.app.todolist.service.JwtService;
import com.app.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    private Map<String, String> response;

    @PostMapping(value = "user")
    public ResponseEntity<?> register(@RequestBody UserDto request) {
        try {
            AuthDto authResponse = userService.register(request);
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        } catch (Exception exception) {
            response = new HashMap<>();
            response.put("message", "Error al crear un nuevo usuario!");
            response.put("token", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "user/username/{token}")
    public ResponseEntity<?> getUsername(@PathVariable String token) {
        try {
            String username = jwtService.getUsernameFromToken(token);
            return new ResponseEntity<>(username, HttpStatus.OK);
        } catch (Exception exception) {
            response = new HashMap<>();
            response.put("message", "Token ha expirado");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
