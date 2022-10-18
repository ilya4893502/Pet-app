package com.example.blog.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WithUserDetails("user")
@Sql(value = {"/person-create-before.sql", "/comment-list.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/comment-list-after.sql", "/person-create-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BlogControllerTest {

    @Autowired
    private BlogController blogController;

    @Autowired
    private MockMvc mockMvc;



    @Test
    void mainPageShouldViewCommentList() throws Exception {
        mockMvc.perform(get("http://localhost:8080/blog/main_page"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/p").string("user"))
                .andExpect(xpath("//div[@id='comment-list']/div").nodeCount(1))
                .andExpect(xpath("//*[@id=\"1\"]").exists());
    }


    @Test
    void postCommentShouldAddCommentToCommentList() throws Exception {

        mockMvc.perform(post("http://localhost:8080/blog").param("commentText", "Hello!")
                        .with(csrf()))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/blog/main_page"));

        mockMvc.perform(get("http://localhost:8080/blog/main_page"))
                .andDo(print())
                .andExpect(xpath("//div[@id='comment-list']/div").nodeCount(2));
    }
}