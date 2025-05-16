package com.aplicatie.Florea_Iulian_java_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Masini_Caracteristici", schema = "dbo")
public class MasinaCaracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "MasinaID")
    private int masinaID;

    @Column(name = "CaracteristicaID")
    private int caracteristicaID;


    public int getMasinaID() {
        return masinaID;
    }

    public void setMasinaID(int masinaID) {
        this.masinaID = masinaID;
    }

    public int getCaracteristicaID() {
        return caracteristicaID;
    }

    public void setCaracteristicaID(int caracteristicaID) {
        this.caracteristicaID = caracteristicaID;
    }
}
