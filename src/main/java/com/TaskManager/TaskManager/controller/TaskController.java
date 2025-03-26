package com.TaskManager.TaskManager.controller;


import com.TaskManager.TaskManager.entity.Task;
import com.TaskManager.TaskManager.entity.User;
import com.TaskManager.TaskManager.service.TaskService;
import com.TaskManager.TaskManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {

    private TaskService taskService;
    private UserService userService;

    @Autowired
    public TaskController(TaskService theTaskService, UserService userService){
        this.taskService=theTaskService;
        this.userService=userService;
    }

    /**
     * Display the list of tasks for the currently logged-in user
     **/
    @GetMapping("/showTasks")
    public String showTasks(Model theModel){

        String currentUsername= SecurityContextHolder.getContext().getAuthentication().getName();

        User theUser=userService.findUserByUsername(currentUsername);

        List<Task> userTasks=taskService.findAllByUser(theUser);

        theModel.addAttribute("tasks", userTasks);

        return "task-list";
    }

    /**
     * Show form to create a new task
     **/
    @GetMapping("/addTasks")
    public String addTasks(Model theModel){

        Task theTask= new Task();

        theModel.addAttribute("task", theTask);

        return "task-form";
    }

   /**
    * Save a new or existing task
    **/
    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task theTask){

        boolean exists=theTask.getId()!=0;

        //Preserve user relationship for an existing task
        if (exists){

            Task existingTask=taskService.findById(theTask.getId());

            theTask.setUser(existingTask.getUser());

        //For a new task, the current user is assigned
        }else if(theTask.getUser()==null){

            String username= SecurityContextHolder.getContext().getAuthentication().getName();

            User theUser=userService.findUserByUsername(username);

            theTask.setUser(theUser);
        }

        taskService.save(theTask);

        return "redirect:/showTasks";
    }

    /**
     * Show form to update an existing task
     **/
    @GetMapping("/update")
    public String updateTask(@RequestParam("taskId") int theId, Model theModel){

        Task theTask=taskService.findById(theId);

        if(theTask==null){
            return "404";
        }

        theModel.addAttribute("task", theTask);

        return "task-form";
    }

   /**
    * Delete a task by ID
    **/
    @GetMapping("/delete")
    public String deleteTask(@RequestParam("taskId") int theId){

        taskService.deleteById(theId);

        return "redirect:/showTasks";
    }
}
