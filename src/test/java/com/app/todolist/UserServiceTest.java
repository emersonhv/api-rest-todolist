package com.app.todolist;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.app.todolist.model.dto.AuthDto;
import com.app.todolist.model.dto.UserDto;
import com.app.todolist.model.entity.user.User;
import com.app.todolist.repository.UserRepository;
import com.app.todolist.service.JwtService;
import com.app.todolist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("password");
        userDto.setName("Test User");

        user = User.builder()
                .username("testuser")
                .password("encodedPassword")
                .name("Test User")
                .build();
    }

    @Test
    void testRegister() {
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.getToken(user)).thenReturn("jwtToken");

        AuthDto authDto = userService.register(userDto);

        assertNotNull(authDto);
        assertEquals("jwtToken", authDto.getToken());
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).getToken(user);
    }

    @Test
    void testGetUserByUsername() {
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByUsername(userDto);

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        verify(userRepository, times(1)).findByUsername(userDto.getUsername());
    }
}

