package com.TaskManager.TaskManager.service;

import com.TaskManager.TaskManager.dao.RoleRepository;
import com.TaskManager.TaskManager.dao.UserRepository;
import com.TaskManager.TaskManager.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImplementation userServiceImplementation;

    @Test
    public void testFindUserByUsername_Found(){

        User theUser = new User();
        String name="Testname";
        theUser.setUsername(name);

        when(userRepository.findByUsername(ArgumentMatchers.any(String.class))).thenReturn(theUser);

        User result=userServiceImplementation.findUserByUsername(name);

        assertEquals(name, result.getUsername());
        verify(userRepository, times(1)).findByUsername(name);

    }

    @Test
    public void testFindUserByUsername_NotFound(){

        String name="Testname";

        when(userRepository.findByUsername(ArgumentMatchers.any(String.class))).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->{
            userServiceImplementation.findUserByUsername(name);
        });

        assertEquals("Did not find User - Testname", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(name);


    }

    @Test
    public void testSaveUser(){

        User theUser = new User();
        theUser.setUsername("Testname");
        theUser.setPassword("Testpassword");

        userServiceImplementation.saveUser(theUser);

        verify(userRepository, times(1)).save(theUser);
    }
}
