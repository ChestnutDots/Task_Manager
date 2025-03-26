package com.TaskManager.TaskManager.controller;


import com.TaskManager.TaskManager.entity.User;
import com.TaskManager.TaskManager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService){
        this.userService=userService;
    }

    /**
     * Display a list of users
     */
    @GetMapping("/showUsers")
    public String showUsers(Model theModel){

        List<User> allUsers=userService.findAllNonAdminUsers();

        theModel.addAttribute("users", allUsers);

        return "admin";
    }

    /**
     * Delete a user account
     */
    @GetMapping("/deleteUsers")
    public String deleteUsers(@RequestParam("userId") int theId){

        userService.deleteById(theId);

        return "redirect:/admin/showUsers";
    }
}
