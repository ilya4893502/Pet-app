package com.example.blog.services;

import com.example.blog.models.Person;
import com.example.blog.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Person savePerson(Person person) {

        String password = passwordEncoder.encode(person.getPassword());
        person.setPassword(password);

        return peopleRepository.save(person);
    }


    public Optional<Person> checkUniq(Person person) {
        Optional<Person> checkPerson = peopleRepository.findByUsername(person.getUsername());
        return checkPerson;
    }
}
