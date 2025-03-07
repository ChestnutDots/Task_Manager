package com.TaskManager.TaskManager.controller;


import com.TaskManager.TaskManager.entity.Task;
import com.TaskManager.TaskManager.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
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
        return "redirect:/showTasks";
    }

    @GetMapping("/update")
    public String updateTask(@RequestParam("taskId") int theId, Model theModel){

        //find the task from the repository:
        Task theTask=taskService.findById(theId);

        // prepopulate the form with information from this task:
        theModel.addAttribute("task", theTask);

        // send out to the form page:
        return "task-form";
    }

    @GetMapping("/delete")
    public String deleteTask(@RequestParam("taskId") int theId){

        //delete the task:
        taskService.deleteById(theId);

        // redirect back to the task list:
        return "redirect:/showTasks";
    }
}
