package com.TaskManager.TaskManager.security;


import com.TaskManager.TaskManager.entity.User;
import com.TaskManager.TaskManager.service.TaskService;
import com.TaskManager.TaskManager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @Test
    public void showTasks_ShouldRedirectToLogin_WhenNotAuthenticated() throws Exception{
        mockMvc.perform(get("/showTasks"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login**"));
    }

    @Test
    @WithMockUser(username="laura", roles={"USER"})
    public void showTasks_ShouldBeAccessible_WhenAuthenticated() throws Exception{
        when(userService.findUserByUsername("linda")).thenReturn(new User());
        when(taskService.findAllByUser(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/showTasks"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="linda", roles={"USER"})
    public void adminPage_ShouldBeForbidden_ForRegularUser() throws Exception{
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void adminPage_ShouldBeAccessible_ForAdmin() throws Exception{
        mockMvc.perform(get("/admin/showUsers"))
                .andExpect(status().isOk());
    }

    @Test
    public void loginPage_ShouldBeAccessibleWithoutAuthentication() throws Exception{
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}
