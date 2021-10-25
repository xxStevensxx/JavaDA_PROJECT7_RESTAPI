package com.nnk.springboot.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
	

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testShowUserAdmin() throws Exception {
        this.mockMvc.perform(get("/user/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testShowUser() throws Exception {
        this.mockMvc.perform(get("/user/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testAddUserAdmin() throws Exception {
        this.mockMvc.perform(get("/user/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testAddUser() throws Exception {
        this.mockMvc.perform(get("/user/add")).andExpect(status().isOk());
    }

//    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testValidateUserAdmin() throws Exception {
        this.mockMvc.perform(post("/user/validate")
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "Test123!")
                .param("role", "USER")
                .with(csrf())
        ).andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testValidateUserAdminHasError() throws Exception {
        this.mockMvc.perform(post("/user/validate")
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "password")
                .param("role", "USER")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

//    @Test
    @WithMockUser
    public void testValidateUser() throws Exception {
        this.mockMvc.perform(post("/user/validate")
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "Test123!")
                .param("role", "USER")
                .with(csrf())
        ).andExpect(redirectedUrl("/user/list"));
    }

//    @Test
    @WithMockUser
    public void testShowUpdateUser() throws Exception {
        User user = userRepository.save(new User("Username", "Test*1235!", "Fullname", "ADMIN"));

        this.mockMvc.perform(get("/user/update/" + user.getId())).andExpect(status().isOk());
    }


//    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testUpdateUserAdmin() throws Exception {
        User user = userRepository.save(new User("Username", "Test*1235!", "Fullname", "ADMIN"));
        this.mockMvc.perform(post("/user/update/" + user.getId())
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "Test123!")
                .param("role", "USER")
                .with(csrf())
        ).andExpect(redirectedUrl("/user/list"));
    }

 //   @Test
    @WithMockUser(authorities = "ADMIN")
    public void testUpdateUserAdminHasError() throws Exception {
        User user = userRepository.save(new User("Username", "Test*1235!", "Fullname", "ADMIN"));
        this.mockMvc.perform(post("/user/update/" + user.getId())
                .param("fullname", "fullname")
                .param("username", "username")
                .param("password", "password")
                .param("role", "USER")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

//    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testDeleteUserAdmin() throws Exception {
        User user = userRepository.save(new User("Username", "Aaaaaaaa7*", "Fullname", "ADMIN"));

        this.mockMvc.perform(get("/user/delete/" + user.getId())).andExpect(status().isFound()).andReturn();
    }

}
