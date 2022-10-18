package com.example.blog.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;



    @Test
    void registrationFormShouldReturnRegistrationPage() throws Exception {
        mockMvc.perform(get("http://localhost:8080/auth/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("/auth/registration"));
    }


    @Test
    void registrationShouldSavePerson() throws Exception {
        mockMvc.perform(post("http://localhost:8080/auth/registration")
                        .param("username", "user").param("password", "user")
                        .param("confirmPassword", "user")
                        .with(csrf()))
                .andExpect(redirectedUrl("/auth/login"));
    }


    @Test
    void loginShouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("http://localhost:8080/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("/auth/login"));
    }


    @Test
    void correctLoginTest() throws Exception {
        mockMvc.perform(formLogin().user("user").password("user"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/auth/login"));
    }


    @Test
    void badCredentials() throws Exception {
        mockMvc.perform(post("/login").param("username", "Alfred")
                        .param("password", "alfred"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}