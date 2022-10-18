package com.example.blog.controllers;

import com.example.blog.models.Person;
import com.example.blog.services.RegistrationService;
import com.example.blog.util.PersonValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValid personValid;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValid personValid) {
        this.registrationService = registrationService;
        this.personValid = personValid;
    }


    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }


    @GetMapping("/registration")
    public String registrationForm(@ModelAttribute("person") Person person) {
        return "/auth/registration";
    }


    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {
        personValid.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        registrationService.savePerson(person);
        return "redirect:/auth/login";
    }
}
