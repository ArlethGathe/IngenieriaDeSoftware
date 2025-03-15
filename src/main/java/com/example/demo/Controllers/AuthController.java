package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Retorna la vista login.html
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // Puedes crear una vista home.html
    }
}

