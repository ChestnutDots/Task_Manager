package com.TaskManager.TaskManager.controller;

import com.TaskManager.TaskManager.entity.User;
import com.TaskManager.TaskManager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username="linda", roles={"ADMIN"})
    public void testShowUsers_ReturnsAListOfUsers() throws Exception{

        User theUser1= new User();
        theUser1.setUsername("testUser1");

        User theUser2= new User();
        theUser2.setUsername("testUser2");

        ArrayList<User> userList= new ArrayList<>();
        userList.add(theUser1);
        userList.add(theUser2);

        when(userService.findAllNonAdminUsers()).thenReturn(userList);

        mockMvc.perform(get("/admin/showUsers"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(username="linda", roles={"ADMIN"})
    public void testDeleteUser_ReturnsToTheListOfUsers() throws Exception{

        int userId=60;

        mockMvc.perform(get("/admin/deleteUsers")
                .param("userId", String.valueOf(userId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/showUsers"));

        verify(userService).deleteById(userId);
    }
}
