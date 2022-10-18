package com.example.blog.util;

import com.example.blog.models.Person;
import com.example.blog.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValid implements Validator {

    private final RegistrationService registrationService;

    @Autowired
    public PersonValid(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (registrationService.checkUniq(person).isPresent()) {
            errors.rejectValue("username", "", "This name already exist");
        }
        if (!person.getPassword().equals(person.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "", "Passwords not coincidence!");
        }
    }
}
