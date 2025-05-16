package com.aplicatie.Florea_Iulian_java_app.controller;

import com.aplicatie.Florea_Iulian_java_app.model.Vanzator;
import com.aplicatie.Florea_Iulian_java_app.repository.VanzatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vanzatori")
public class VanzatorController {

    @Autowired
    private VanzatorRepository vanzatorRepository;

    @GetMapping
    public String getAllVanzatori(Model model) {
        List<Vanzator> vanzatori = vanzatorRepository.getVanzatori();
        model.addAttribute("vanzatori", vanzatori);
        return "vanzatori";
    }

    @PostMapping("/add")
    public String addVanzator(@ModelAttribute Vanzator vanzator) {
        vanzatorRepository.save(vanzator);
        return "redirect:/vanzatori";
    }

    @PostMapping("/delete/{id}")
    public String deleteVanzator(@PathVariable int id) {
        vanzatorRepository.delete(id);
        return "redirect:/vanzatori";
    }

    @GetMapping("/edit/{id}")
    public String editVanzator(@PathVariable int id, Model model) {
        Vanzator vanzator = vanzatorRepository.getVanzator(id);
        model.addAttribute("vanzator", vanzator);
        return "edit-vanzator";
    }

    @PostMapping("/update")
    public String updateVanzator(@ModelAttribute Vanzator vanzator) {
        vanzatorRepository.update(vanzator);
        return "redirect:/vanzatori";
    }



}
