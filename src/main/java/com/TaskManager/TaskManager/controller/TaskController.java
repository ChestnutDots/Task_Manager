package com.TaskManager.TaskManager.controller;


import com.TaskManager.TaskManager.entity.Task;
import com.TaskManager.TaskManager.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService theTaskService){
        taskService=theTaskService;
    }

    @GetMapping("/showTasks")
    public String showTasks(Model theModel){

        // show all tasks
        List<Task> theTasks= taskService.findAll();

        // add the list to the model:
        theModel.addAttribute("tasks", theTasks);

        return "show-tasks";
    }

    @GetMapping("/addTasks")
    public String addTasks(Model theModel){

        // create a new task (empty):
        Task theTask= new Task();

        // add it to the model:
        theModel.addAttribute("task", theTask);

        return "task-form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task theTask){

        //save the task:
        taskService.save(theTask);

        //us a redirect:
        return "redirect:/tasks/showTasks";
    }
}
