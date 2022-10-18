package com.example.blog.services;

import com.example.blog.models.Person;
import com.example.blog.repositories.PeopleRepository;
import org.checkerframework.checker.nullness.Opt;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RegistrationServiceTest {

    @Autowired
    private RegistrationService registrationService;

    @MockBean
    private PeopleRepository peopleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void savePersonShouldAddPersonInDatabase() {
        Person person = new Person(7, "username", "password");
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        assertTrue(CoreMatchers.is(person.getPassword()).matches(passwordEncoder.encode("password")));
        Person person1 = registrationService.savePerson(person);
        assertEquals(person1, registrationService.savePerson(person));
    }


    @Test
    void checkUniq() {
        Person person = new Person(7, "username", "password");
        Optional <Person> person1 = peopleRepository.findByUsername(person.getUsername());
        assertEquals(person1, peopleRepository.findByUsername(person.getUsername()));
    }
}