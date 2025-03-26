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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testAddUser_ReturnTheUserAdded() throws Exception{
        mockMvc.perform(get("/addUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testSaveUser_SuccesfulRegistration_RedirectsToLogin() throws Exception{
        User theUser = new User();
        theUser.setUsername("testUser");
        theUser.setPassword("testPassword");

        mockMvc.perform(post("/saveUser")
                .flashAttr("user", theUser))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testSaveUser_RegistrationFails_ReturnsToFormWithError() throws Exception{
        User user= new User();
        user.setUsername("duplicateUser");

        RuntimeException ex= new RuntimeException("Username already exists");
        doThrow(ex).when(userService).saveUser(any(User.class));

        mockMvc.perform(post("/saveUser")
                .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("user-register"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("errorMessage", "Username already exists"));
    }

    @Test
    @WithMockUser("laura")
    public void testDeleteUser_ShouldCallServiceAndRedirect() throws Exception{

        int userId=60;

        User theUser= new User();
        theUser.setUsername("laura");
        theUser.setId(userId);

        when(userService.findUserByUsername("laura")).thenReturn(theUser);

        mockMvc.perform(get("/deleteUser")
                .param("userId", String.valueOf(userId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService).deleteById(userId);
    }
}
