package com.app.todolist.service;

import com.app.todolist.repository.UserRepository;
import com.app.todolist.model.entity.user.User;
import com.app.todolist.model.dto.AuthDto;
import com.app.todolist.model.dto.LoginDto;
import com.app.todolist.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthSevice {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Transactional
    public AuthDto login(LoginDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(userDetails);
        return AuthDto.builder()
                .token(token)
                .build();
    }
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
}
