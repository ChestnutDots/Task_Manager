package com.TaskManager.TaskManager.controller;


import com.TaskManager.TaskManager.entity.User;
import com.TaskManager.TaskManager.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    /**
     * Display user registration form
     */
    @GetMapping("/addUser")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "user-register";
    }


    /**
     *  Process registration form and save user
     */
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User theUser, Model model){

        try{
            userService.saveUser(theUser);

            return "redirect:/login";

        } catch(RuntimeException ex){

            model.addAttribute("user", theUser);
            model.addAttribute("errorMessage", ex.getMessage());

            return "user-register";
        }

    }

    /**
     * Delete currently logged-in user
     */
    @GetMapping("/deleteUser")
    public String deleteUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername=authentication.getName();

        User theUser=userService.findUserByUsername(currentUsername);

        userService.deleteById(theUser.getId());

        return "redirect:/login";
    }
}
