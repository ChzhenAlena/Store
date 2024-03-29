package org.example.security_app.controllers;

import jakarta.validation.Valid;
import org.example.security_app.models.Person;
import org.example.security_app.services.RegistrationService;
import org.example.security_app.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String registration(@ModelAttribute("person")Person person){
        return "auth/registration";
    }
    @PostMapping("/register")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "/auth/registration";
        registrationService.register(person);
        return "redirect:/login";

    }
}
