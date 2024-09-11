package com.app.todolist.service;

import com.app.todolist.model.dto.AuthDto;
import com.app.todolist.model.dto.UserDto;
import com.app.todolist.model.entity.user.User;
import com.app.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public AuthDto register(UserDto request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        userRepository.save(user);

        return AuthDto.builder().token(jwtService.getToken(user)).build();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(UserDto request) {
        return userRepository.findByUsername(request.getUsername());
    }
}
