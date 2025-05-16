package com.aplicatie.Florea_Iulian_java_app.controller;

import com.aplicatie.Florea_Iulian_java_app.model.Marca;
import com.aplicatie.Florea_Iulian_java_app.repository.ClientRepository;
import com.aplicatie.Florea_Iulian_java_app.repository.MarcaRepository;
import com.aplicatie.Florea_Iulian_java_app.repository.StatisticiRepository;
import com.aplicatie.Florea_Iulian_java_app.repository.VanzareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



@Controller
@RequestMapping
public class StatisticiController {

    @Autowired
    private StatisticiRepository statisticiRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/statistici")
    public String showStatistici() {
        return "statistici";
    }

    @GetMapping("/statistici/vanzari-interval")
    public String afisareStatisticiPeInterval(
            @RequestParam(required = false) String dataInceput,
            @RequestParam(required = false) String dataSfarsit,
            Model model) {
        if (dataInceput != null && dataSfarsit != null) {
            List<Object[]> clientiMasini = statisticiRepository.getClientiMasiniPeInterval(dataInceput, dataSfarsit);
            model.addAttribute("clientiMasini", clientiMasini);
        }
        model.addAttribute("dataInceput", dataInceput);
        model.addAttribute("dataSfarsit", dataSfarsit);
        return "vanzari-interval";
    }

    @GetMapping("/statistici/top-marci-modele")
    public String getTopMarciSiModele(Model model) {
        List<Object[]> topMarci = statisticiRepository.getTopMarciVandute();
        List<Object[]> topModele = statisticiRepository.getTopModeleVandute();
        model.addAttribute("topMarci", topMarci);
        model.addAttribute("topModele", topModele);
        return "top-marci-modele";
    }

    @GetMapping("/statistici/top-vanzatori")
    public String getTopVanzatoriAllTime(Model model) {
        List<Object[]> topVanzatoriAllTime = statisticiRepository.getTopVanzatori();
        model.addAttribute("topVanzatoriAllTime", topVanzatoriAllTime);
        return "top-vanzatori";
    }




    @GetMapping("/statistici/top-vanzatori-interval")
    public String getTopVanzatoriPeInterval(
            @RequestParam String dataInceput,
            @RequestParam String dataSfarsit,
            Model model) {
        List<Object[]> topVanzatoriInterval = statisticiRepository.getTopVanzatoriPeInterval(dataInceput, dataSfarsit);
        model.addAttribute("topVanzatoriAllTime", statisticiRepository.getTopVanzatori());
        model.addAttribute("topVanzatoriInterval", topVanzatoriInterval);
        model.addAttribute("dataInceput", dataInceput);
        model.addAttribute("dataSfarsit", dataSfarsit);
        return "top-vanzatori";
    }


    @GetMapping("/statistici/venituri-vanzatori")
    public String getVenituriSiNumarMasiniPerVanzator(Model model) {
        List<Object[]> venituriSiMasini = statisticiRepository.getVenituriSiNumarMasiniPerVanzator();
        model.addAttribute("venituriSiMasini", venituriSiMasini);
        return "venituri-vanzatori"; // Redirecționează către pagina HTML
    }


    @GetMapping("/statistici/cel-mai-profitabil-model")
    public String getCelMaiProfitabilModelPentruFiecareVanzator(Model model) {
        List<Object[]> profitabilModel = statisticiRepository.getCelMaiProfitabilModelPentruFiecareVanzator();
        model.addAttribute("profitabilModel", profitabilModel);
        return "cel-mai-profitabil-model";
    }


    @GetMapping("/statistici/clienti-fani-marca")
    public String getClientiFaniMarca(@RequestParam(required = false) String marca, Model model) {
        List<Marca> marci = marcaRepository.getAllMarci();
        List<String> numeMarci = marci.stream().map(Marca::getNume).distinct().sorted().toList();
        model.addAttribute("marci", numeMarci);

        if (marca != null) {
            List<Object[]> clienti = statisticiRepository.getClientiFaniMarca(marca);
            model.addAttribute("clientiFaniMarca", clienti);
        }

        return "clienti-fani-marca";
    }



    @GetMapping("/statistici/clienti-peste-medie")
    public String getClientiPesteMedia(Model model) {
        List<Object[]> clientiPesteMedia = statisticiRepository.getClientiCuMasiniPesteMediaPreturilor();
        model.addAttribute("clientiPesteMedia", clientiPesteMedia);
        return "clienti-peste-medie";
    }



    @GetMapping("/statistici/clienti-metode-plata")
    public String clientiMetodePlata(@RequestParam(value = "metodaPlata", required = false) String metodaPlata,
                                     Model model) {
        List<String> metodePlata = statisticiRepository.getDistinctMetodePlata();
        model.addAttribute("metodePlata", metodePlata);

        if (metodaPlata != null && !metodaPlata.isEmpty()) {
            List<Object[]> clienti = statisticiRepository.getClientiByMetodaPlata(metodaPlata);
            model.addAttribute("clienti", clienti);
            model.addAttribute("selectedMetodaPlata", metodaPlata);
        }

        return "clienti-metode-plata";
    }



    @GetMapping("/statistici/top-luni-vanzari")
    public String getTop3LuniCuCeleMaiMulteVanzari(Model model) {
        List<Object[]> topLuniVanzari = statisticiRepository.getTop3LuniCuCeleMaiMulteVanzari();
        model.addAttribute("topLuniVanzari", topLuniVanzari);
        return "top-luni-vanzari";
    }


}


