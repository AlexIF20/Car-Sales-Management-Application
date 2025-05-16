package com.aplicatie.Florea_Iulian_java_app.controller;

import com.aplicatie.Florea_Iulian_java_app.model.*;


import com.aplicatie.Florea_Iulian_java_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/masini")
public class MasinaController {

    @Autowired
    private MasinaRepository masinaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModelMRepository modelMRepository;

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    @Autowired
    private MasinaCaracteristicaRepository masinaCaracteristicaRepository;

    @Autowired
    private VanzareRepository vanzareRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VanzatorRepository vanzatorRepository;



    @GetMapping
    public String getAllMasini(Model model) {
        List<Masina> masini = masinaRepository.getMasini();
        List<Object[]> masiniDetalii = masini.stream().map(masina -> {
            String numeModel = modelMRepository.getModelById(masina.getModelID()).getNumeModel();
            String numeMarca = marcaRepository.getMarcaNumeByModelId(masina.getModelID());
            return new Object[]{
                    numeMarca,
                    numeModel,
                    masina.getAnFabricatie(),
                    masina.getVin(),
                    masina.getMasinaID(),
                    masina.getVanduta()
            };
        }).collect(Collectors.toList());

        model.addAttribute("masini", masiniDetalii);
        model.addAttribute("marci", marcaRepository.getAllMarci());
        model.addAttribute("modele", modelMRepository.getAllModels());

        return "masini";
    }

    @PostMapping("/add")
    public String addMasina(@ModelAttribute Masina masina) {
        masinaRepository.save(masina);
        return "redirect:/masini";
    }

    @PostMapping("/delete/{id}")
    public String deleteMasina(@PathVariable int id) {
        masinaRepository.delete(id);
        return "redirect:/masini";
    }

    @PostMapping("/getModelsByMarca")
    @ResponseBody
    public List<ModelM> getModelsByMarca(@RequestParam int marcaID) {
        return modelMRepository.getAllModels().stream()
                .filter(model -> model.getMarcaID() == marcaID)
                .collect(Collectors.toList());
    }


    @GetMapping("/detalii/{vin}")
    public String masinaDetalii(@PathVariable String vin, Model model) {
        Masina masina = masinaRepository.getMasini().stream()
                .filter(m -> m.getVin().equals(vin))
                .findFirst()
                .orElse(null);

        if (masina == null) {
            return "redirect:/masini";
        }

        String numeModel = modelMRepository.getModelById(masina.getModelID()).getNumeModel();
        String numeMarca = marcaRepository.getMarcaNumeByModelId(masina.getModelID());

        List<MasinaCaracteristica> masinaCaracteristici = masinaCaracteristicaRepository
                .getCaracteristiciByMasinaId(masina.getMasinaID());

        List<Caracteristica> caracteristici = masinaCaracteristici.stream()
                .map(mc -> caracteristicaRepository.getCaracteristica(mc.getCaracteristicaID()))
                .collect(Collectors.toList());

        List<Client> clienti = clientRepository.getClienti();
        List<Vanzator> vanzatori = vanzatorRepository.getVanzatori();

        model.addAttribute("masina", masina);
        model.addAttribute("marca", numeMarca);
        model.addAttribute("model", numeModel);
        model.addAttribute("clienti", clienti);
        model.addAttribute("vanzatori", vanzatori);
        model.addAttribute("caracteristici", caracteristici);
        model.addAttribute("toateCaracteristicile", caracteristicaRepository.getCaracteristici());

        if (masina.getVanduta()) {
            Vanzare vanzare = vanzareRepository.getVanzari().stream()
                    .filter(v -> v.getMasinaID() == masina.getMasinaID())
                    .findFirst()
                    .orElse(null);

            if (vanzare != null) {
                Client client = clientRepository.getClient(vanzare.getClientID());
                Vanzator vanzator = vanzatorRepository.getVanzator(vanzare.getVanzatorID());
                model.addAttribute("client", client);
                model.addAttribute("vanzator", vanzator);
            }
        }

        return "detalii-masina";
    }






    @PostMapping("/addCaracteristica")
    public String addCaracteristicaToMasina(@RequestParam int masinaID, @RequestParam int caracteristicaID) {
        // Creați un obiect MasinaCaracteristica
        MasinaCaracteristica masinaCaracteristica = new MasinaCaracteristica();
        masinaCaracteristica.setMasinaID(masinaID);
        masinaCaracteristica.setCaracteristicaID(caracteristicaID);

        // Adăugați caracteristica doar dacă nu există deja
        masinaCaracteristicaRepository.save(masinaCaracteristica);

        String vin = masinaRepository.getMasini(masinaID).getVin();
        return "redirect:/masini/detalii/" + vin;  // Redirecționăm la detalii mașină
    }




    @PostMapping("/detalii/{vin}/deleteCaracteristica/{caracteristicaID}")
    public String stergeCaracteristica(@PathVariable String vin, @PathVariable int caracteristicaID) {
        Masina masina = masinaRepository.getMasini().stream()
                .filter(m -> m.getVin().equals(vin))
                .findFirst()
                .orElse(null);

        if (masina != null) {
            masinaCaracteristicaRepository.deleteCaracteristica(masina.getMasinaID(), caracteristicaID);
        }

        return "redirect:/masini/detalii/" + vin;
    }



    @PostMapping("/transformaInVanzare")
    public String transformaInVanzare(
            @RequestParam int masinaID,
            @RequestParam String clientCNP,
            @RequestParam String vanzatorCNP,
            @RequestParam String metodaPlata,
            @RequestParam BigDecimal pretVanzare,
            @RequestParam String dataVanzare) {

        Masina masina = masinaRepository.getMasini(masinaID);
        masina.setVanduta(true);
        masinaRepository.updateMasina(masina);

        Vanzare vanzare = new Vanzare();
        vanzare.setMasinaID(masinaID);
        vanzare.setClientID(clientRepository.getClientByCNP(clientCNP).getClientID());
        vanzare.setVanzatorID(vanzatorRepository.getVanzatorByCNP(vanzatorCNP).getVanzatorID());
        vanzare.setMetodaPlata(metodaPlata);
        vanzare.setPretVanzare(pretVanzare);

        LocalDate localDate = LocalDate.parse(dataVanzare);
        vanzare.setDataVanzarii(localDate.atStartOfDay());

        vanzareRepository.save(vanzare);

        return "redirect:/masini/detalii/" + masina.getVin();
    }



    @PostMapping("/updateVanduta")
    public String updateVanduta(@RequestParam int masinaID) {
        Masina masina = masinaRepository.getMasini(masinaID);
        masina.setVanduta(!masina.getVanduta());
        masinaRepository.updateMasina(masina);
        return "redirect:/masini/detalii/" + masina.getVin();
    }




}
