package com.example.blog.services;

import com.example.blog.models.Comment;
import com.example.blog.models.Person;
import com.example.blog.repositories.CommentsRepository;
import com.example.blog.repositories.PeopleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CommentsServiceTest {

    @Autowired
    private CommentsService commentsService;

    @MockBean
    private CommentsRepository commentsRepository;

    @MockBean
    private PeopleRepository peopleRepository;

    @Test
    void allCommentsShouldReturnCommentsList() {
        List<Comment> comments = commentsService.allComments();
        assertEquals(comments, commentsService.allComments());
    }

    @Test
    void postCommentShouldSaveComment() {

        Person person = new Person(7, "username", "password");
        Comment comment = new Comment(7, "Hello!", new Date(), person);
        List<Comment> comments = new ArrayList<>();
        person.setComments(comments);

        boolean isAddCommentToPerson = person.getComments().add(comment);
        assertTrue(isAddCommentToPerson);

        Comment comment1 = commentsRepository.save(comment);
        assertEquals(comment1, commentsRepository.save(comment));
    }
}