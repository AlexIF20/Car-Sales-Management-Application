package com.aplicatie.Florea_Iulian_java_app.controller;

import com.aplicatie.Florea_Iulian_java_app.repository.*;

import com.aplicatie.Florea_Iulian_java_app.model.Masina;
import com.aplicatie.Florea_Iulian_java_app.model.Marca;
import com.aplicatie.Florea_Iulian_java_app.model.Vanzare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vanzari")
public class VanzareController {

    @Autowired
    private VanzareRepository vanzareRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private VanzatorRepository vanzatorRepository;

    @Autowired
    private MasinaRepository masinaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModelMRepository modelMRepository;

    @GetMapping
    public String getAllVanzari(Model model) {
        List<Object[]> vanzariDetaliate = vanzareRepository.getVanzariDetaliate();

        model.addAttribute("vanzari", vanzariDetaliate);
        return "vanzari";
    }


    @PostMapping("/delete/{id}")
    public String deleteVanzare(@PathVariable int id) {
        Vanzare vanzare = vanzareRepository.getVanzare(id);
        if (vanzare != null) {
            Masina masina = masinaRepository.getMasini(vanzare.getMasinaID());
            if (masina != null) {
                masina.setVanduta(false);
                masinaRepository.updateMasina(masina);
            }

            vanzareRepository.delete(id);
        }
        return "redirect:/vanzari";
    }



}
