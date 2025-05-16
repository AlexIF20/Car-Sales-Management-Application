package com.aplicatie.Florea_Iulian_java_app.controller;

import com.aplicatie.Florea_Iulian_java_app.model.Client;
import com.aplicatie.Florea_Iulian_java_app.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clienti")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public String getAllClienti(Model model) {
        List<Client> clienti = clientRepository.getClienti();
        model.addAttribute("clienti", clienti);
        return "clienti";
    }

    @PostMapping("/add")
    public String addClient(@ModelAttribute Client client) {
        clientRepository.save(client);
        return "redirect:/clienti";
    }

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable int id) {
        System.out.println("Cerere primită pentru ștergerea clientului cu ID: " + id);
        clientRepository.delete(id);
        return "redirect:/clienti";
    }

    @GetMapping("/edit/{id}")
    public String editClient(@PathVariable int id, Model model) {
        Client client = clientRepository.getClient(id);
        model.addAttribute("client", client);
        return "edit-client";
    }

    @PostMapping("/update")
    public String updateClient(@ModelAttribute Client client) {
        clientRepository.update(client);
        return "redirect:/clienti";
    }





}
