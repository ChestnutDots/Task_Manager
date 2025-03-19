package com.TaskManager.TaskManager.service;

import com.TaskManager.TaskManager.dao.RoleRepository;
import com.TaskManager.TaskManager.dao.UserRepository;
import com.TaskManager.TaskManager.entity.Role;
import com.TaskManager.TaskManager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImplementation implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository theUserRepository,
                                     PasswordEncoder passwordEncoder, RoleRepository roleRepository){

        this.userRepository=theUserRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User theUser) {

        //Hash the password before saving:
        theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));

        userRepository.save(theUser);

        Role role= new Role("ROLE_USER", theUser);
        roleRepository.save(role);
    }



}
