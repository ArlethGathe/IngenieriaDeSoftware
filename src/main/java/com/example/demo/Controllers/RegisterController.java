package com.example.demo.controllers;

import com.example.demo.models.Usuario;
import com.example.demo.models.Rol;
import com.example.demo.repositories.RolRepository;
import com.example.demo.services.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections;
import java.util.Optional;

@Controller
public class RegisterController {

    private final UsuarioService usuarioService;
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;

    public RegisterController(UsuarioService usuarioService, RolRepository rolRepository, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.rolRepository = rolRepository;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        // Buscar el rol "ROLE_USER"
        Optional<Rol> rolUsuarioOpt = rolRepository.findByNombre("ROLE_USER");

        if (rolUsuarioOpt.isEmpty()) {
            throw new RuntimeException("El rol ROLE_USER no existe en la base de datos");
        }

        usuario.setRoles(Collections.singleton(rolUsuarioOpt.get()));

        // Registra al usuario en la base de datos con la contraseña encriptada
        usuarioService.registrarUsuario(usuario);

        // Autenticar automáticamente después del registro
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Redirigir directamente a home después del registro
        return "redirect:/home";
    }
}
