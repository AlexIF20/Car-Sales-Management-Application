package com.aplicatie.Florea_Iulian_java_app.controller;

import com.aplicatie.Florea_Iulian_java_app.model.User;
import com.aplicatie.Florea_Iulian_java_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/add-user")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute User user, Model model) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("errorMessage", "Utilizatorul deja există!");
            return "add-user";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
}
