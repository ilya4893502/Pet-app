package com.example.blog.services;

import com.example.blog.models.Comment;
import com.example.blog.models.Person;
import com.example.blog.repositories.CommentsRepository;
import com.example.blog.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository, PeopleRepository peopleRepository) {
        this.commentsRepository = commentsRepository;
        this.peopleRepository = peopleRepository;
    }


    public List<Comment> allComments() {
        return commentsRepository.findAll();
    }


    @Transactional
    public Comment postComment(Comment comment, Person personPostedComment) {
        comment.setPostDateAndTime(new Date());
        comment.setPerson(personPostedComment);
        Person person = peopleRepository.findById(personPostedComment.getId()).get();
        if (person.getComments() == null) {
            person.setComments(new ArrayList(List.of(person)));
        } else {
            person.getComments().add(comment);
        }

        return commentsRepository.save(comment);
    }
}
