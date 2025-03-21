package com.TaskManager.TaskManager.controller;

import com.TaskManager.TaskManager.dao.UserRepository;
import com.TaskManager.TaskManager.entity.User;
import com.TaskManager.TaskManager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private UserService userService;
    private static final Logger logger= LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, UserRepository userRepository){
        this.userService=userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/addUser")
    public String addUser(Model theModel){
        theModel.addAttribute("user", new User());
        return "user-register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User theUser, Model theModel){

        logger.info("Received registration for user: {}", theUser.getUsername());

        try{
            userService.saveUser(theUser);
            return "redirect:/login";
        } catch(RuntimeException ex){
            theModel.addAttribute("user", theUser);
            theModel.addAttribute("errorMessage", ex.getMessage());
            return "user-register";
        }

    }
}
