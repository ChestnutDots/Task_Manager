package com.TaskManager.TaskManager.service;

import com.TaskManager.TaskManager.dao.RoleRepository;
import com.TaskManager.TaskManager.dao.TaskRepository;
import com.TaskManager.TaskManager.dao.UserRepository;
import com.TaskManager.TaskManager.entity.Role;
import com.TaskManager.TaskManager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing user-related operations, including
 * registration, retrieval and deletion with role/task associations.
 */
@Service
public class UserServiceImplementation implements UserService{

    private final TaskRepository taskRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository theUserRepository,
                                     PasswordEncoder passwordEncoder, RoleRepository roleRepository, TaskRepository taskRepository){

        this.userRepository=theUserRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
        this.taskRepository = taskRepository;
    }

    @Override
    public User findUserByUsername(String username) {

        User user=userRepository.findByUsername(username);
        if(user==null){
            throw new RuntimeException("Did not find User - "+username);
        }

        return user;
    }

    /**
     * Save a new user to the database.
     * Check that the username is unique.
     * Encrypt the password.
     * Add the user id to the roles table.
     * Assign a default 'ROLE_USER'
     */
    @Override
    public void saveUser(User theUser) {

        if (userRepository.findByUsername(theUser.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        if(theUser.getPassword()==null){
            throw new IllegalArgumentException("Password cannot be null");
        }

        theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));

        userRepository.save(theUser);

        Role role= new Role("ROLE_USER", theUser);
        roleRepository.save(role);
    }


    @Override
    public List<User> findAllNonAdminUsers() {
        return userRepository.findAllNonAdminUsers();
    }

    /**
     * Deletes a user with all associated roles and tasks.
     */
    @Override
    @Transactional
    public void deleteById(int theId) {

        roleRepository.deleteByUserId(theId);
        taskRepository.deleteByUserId(theId);
        userRepository.deleteById(theId);
    }


}
