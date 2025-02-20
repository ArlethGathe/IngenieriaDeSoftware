package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hola")
    public String holaSpring(Model model) {
        model.addAttribute("mensaje", "Hola Spring");
        return "hola"; // Nombre del archivo HTML en /templates
    }
}

