package com.TaskManager.TaskManager.service;

import com.TaskManager.TaskManager.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {

    User findUserByUsername(String username);

    void saveUser(User theUser);

}
