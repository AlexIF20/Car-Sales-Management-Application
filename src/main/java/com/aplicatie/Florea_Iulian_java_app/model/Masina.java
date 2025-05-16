package com.aplicatie.Florea_Iulian_java_app.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Masini", schema = "dbo")
public class Masina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MasinaID")
    private int masinaID;

    @Column(name = "ModelID")
    private int modelID;

    @Column(name = "An_Fabricatie")
    private LocalDate anFabricatie;

    @Column(name = "VIN")
    private String vin;

    @Column(name = "Vanduta")
    private boolean vanduta;

    @OneToMany
    @JoinColumn(name = "MasinaID", referencedColumnName = "MasinaID")
    private List<MasinaCaracteristica> caracteristici;


    public int getMasinaID() {
        return masinaID;
    }

    public int getModelID() {
        return modelID;
    }

    public boolean getVanduta() {
        return vanduta;
    }

    public void setVanduta(boolean vanduta) {
        this.vanduta = vanduta;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public LocalDate getAnFabricatie() {
        return anFabricatie;
    }

    public void setAnFabricatie(LocalDate anFabricatie) {
        this.anFabricatie = anFabricatie;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public List<MasinaCaracteristica> getCaracteristici() {
        return caracteristici;
    }

    public void setCaracteristici(List<MasinaCaracteristica> caracteristici) {
        this.caracteristici = caracteristici;
    }


}
