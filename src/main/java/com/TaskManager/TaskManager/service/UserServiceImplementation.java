package com.TaskManager.TaskManager.service;

import com.TaskManager.TaskManager.dao.RoleRepository;
import com.TaskManager.TaskManager.dao.UserRepository;
import com.TaskManager.TaskManager.entity.Role;
import com.TaskManager.TaskManager.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImplementation implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

    @Autowired
    public UserServiceImplementation(UserRepository theUserRepository,
                                     PasswordEncoder passwordEncoder, RoleRepository roleRepository){

        this.userRepository=theUserRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public User findUserByUsername(String username) {

        User user=userRepository.findByUsername(username);
        if(user==null){
            throw new RuntimeException("Did not find User - "+username);
        }

        return user;
    }

    @Override
    public void saveUser(User theUser) {

        logger.info("Checking if username already exists: {}", theUser.getUsername());

        if (userRepository.findByUsername(theUser.getUsername()) != null) {
            logger.warn("Username '{}' already exists! Throwing exception.", theUser.getUsername());
            throw new RuntimeException("Username already exists");
        }

        if(theUser.getPassword()==null){
            throw new IllegalArgumentException("Password cannot be null");
        }

        //Hash the password before saving:
        theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));

        userRepository.save(theUser);

        Role role= new Role("ROLE_USER", theUser);
        roleRepository.save(role);
    }



}
