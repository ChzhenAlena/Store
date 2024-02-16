package org.example.security_app.controllers;

import org.example.security_app.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping
    public String registration(@ModelAttribute("person")Person person){
        return "auth/registration";
    }
}
