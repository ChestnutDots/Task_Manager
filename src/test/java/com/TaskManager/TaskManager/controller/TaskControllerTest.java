package com.TaskManager.TaskManager.controller;

import com.TaskManager.TaskManager.entity.Task;
import com.TaskManager.TaskManager.entity.User;
import com.TaskManager.TaskManager.service.TaskService;
import com.TaskManager.TaskManager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser("linda")
    public void testShowTasks_ReturnsTaskListViewWithTasks() throws Exception{

        User theUser = new User();
        theUser.setUsername("linda");

        Task task1 = new Task();
        task1.setTaskDescription("Walk the dog");

        Task task2= new Task();
        task2.setTaskDescription("Water the flowers");

        ArrayList<Task> testList= new ArrayList();
        testList.add(task1);
        testList.add(task2);

        when(userService.findUserByUsername("linda")).thenReturn(theUser);
        when(taskService.findAllByUser(theUser)).thenReturn(testList);

        mockMvc.perform(get("/showTasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("task-list"))
                .andExpect(model().attributeExists("tasks"));

    }

    @Test
    public void testAddTasks_ReturnTheTaskAdded() throws Exception{
        mockMvc.perform(get("/addTasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("task-form"))
                .andExpect(model().attributeExists("task"));

    }

    @Test
    @WithMockUser(username="linda")
    public void testSaveNewTask_AssignsUserAndRedirects() throws Exception{

        User theUser = new User();
        theUser.setUsername("linda");

        Task theTask= new Task();
        theTask.setId(0);

        when(userService.findUserByUsername("linda")).thenReturn(theUser);

        mockMvc.perform(post("/save")
                .flashAttr("task", theTask))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/showTasks"));

        verify(taskService).save(argThat(task ->
                task.getUser() == theUser &&
                task.getId() == 0
        ));
    }

    @Test
    @WithMockUser(username="linda")
    public void testSaveExistingTask_PreservesUserAndRedirects() throws Exception{

        User existingUser = new User();
        existingUser.setUsername("linda");

        Task existingTask = new Task();
        existingTask.setId(1);
        existingTask.setUser(existingUser);

        Task incomingTask = new Task();
        incomingTask.setId(1);

        when(taskService.findById(1)).thenReturn(existingTask);

        mockMvc.perform(post("/save")
                .flashAttr("task", incomingTask))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/showTasks"));

        verify(taskService).save(argThat(task ->
                task.getUser() == existingUser &&
                task.getId() ==1
        ));
    }

    @Test
    @WithMockUser("linda")
    public void testUpdateTask_ReturnsTaskFormWithExistingTask() throws Exception{

        Task mockTask = new Task();
        mockTask.setId(42);
        mockTask.setTaskDescription("Update me");

        when(taskService.findById(42)).thenReturn(mockTask);

        mockMvc.perform(get("/update")
                .param("taskId", "42"))
                .andExpect(status().isOk())
                .andExpect(view().name("task-form"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attribute("task", mockTask));
    }

    @Test
    @WithMockUser("linda")
    public void testUpdateTask_WhenTaskNotFound_ShouldReturnErrorPageOrThrow() throws Exception{

        int taskId=999;
        when(taskService.findById(taskId)).thenReturn(null);

        mockMvc.perform(get("/update")
                .param("taskId", String.valueOf(taskId)))
                .andExpect(status().isOk())
                .andExpect(view().name("404"));
    }

    @Test
    @WithMockUser("linda")
    public void testDeleteTask_ShouldCallServiceAndRedirect() throws Exception{

        int taskId=42;

        mockMvc.perform(get("/delete")
                .param("taskId", String.valueOf(taskId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/showTasks"));

        verify(taskService).deleteById(taskId);
    }
}
