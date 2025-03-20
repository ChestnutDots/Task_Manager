package com.TaskManager.TaskManager.controller;

import com.TaskManager.TaskManager.entity.User;
import com.TaskManager.TaskManager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/addUser")
    public String addUser(Model theModel){
        theModel.addAttribute("user", new User());
        return "user-register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User theUser){

        userService.saveUser(theUser);

        return "redirect:/login";
    }
}
