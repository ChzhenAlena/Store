package org.example.security_app.services;

import org.example.security_app.models.Person;
import org.example.security_app.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> showPeople(){
        return peopleRepository.findAll();
    }
    public void lockPerson(int id){
        Person person = peopleRepository.findById(id).get();
        person.setLocked(true);
        peopleRepository.save(person);
    }
    public Person findById(int id){
        return peopleRepository.findById(id).get();
    }
}
