package com.TaskManager.TaskManager.service;

import com.TaskManager.TaskManager.entity.User;

import java.util.List;


public interface UserService {

    User findUserByUsername(String username);

    void saveUser(User theUser);

    List<User>findAllNonAdminUsers();

    void deleteById(int theId);
}
