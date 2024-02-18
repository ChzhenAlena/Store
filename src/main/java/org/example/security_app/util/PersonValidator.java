package org.example.security_app.util;

import org.example.security_app.models.Person;
import org.example.security_app.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(peopleRepository.findByUsername(((Person) target).getUsername()).isEmpty())
            return;
        errors.rejectValue("username", "", "User with this username is already exists");
    }
}
