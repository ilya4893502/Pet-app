package com.example.blog.controllers;

import com.example.blog.models.Comment;
import com.example.blog.models.Person;
import com.example.blog.security.PersonDetails;
import com.example.blog.services.CommentsService;
import com.example.blog.services.PeopleService;
import com.example.blog.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final PeopleService peopleService;
    private final CommentsService commentsService;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public BlogController(PeopleService peopleService, CommentsService commentsService, PersonDetailsService personDetailsService) {
        this.peopleService = peopleService;
        this.commentsService = commentsService;
        this.personDetailsService = personDetailsService;
    }


    @GetMapping("/main_page")
    public String mainPage(@ModelAttribute("comment") Comment comment, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        model.addAttribute("person", person);
        model.addAttribute("comments", commentsService.allComments());
        return "/mainPage";
    }


    @PostMapping()
    public String postComment(@ModelAttribute("comment") @Valid Comment comment,
                              BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();

        if (bindingResult.hasErrors()) {
            return "redirect:/blog/main_page?error";
        }
        commentsService.postComment(comment, person);
        return "redirect:/blog/main_page";
    }
}
