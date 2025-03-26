package com.TaskManager.TaskManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * Display log-in form
     */
    @GetMapping("/login")
    public String showMyLoginPage(){
        return "login";
    }

}
