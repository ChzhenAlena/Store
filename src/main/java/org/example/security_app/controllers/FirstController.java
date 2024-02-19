package org.example.security_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }
    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }
}
