package com.aplicatie.Florea_Iulian_java_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Caracteristici", schema = "dbo")
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CaracteristicaID")
    private int caracteristicaID;

    @Column(name = "Nume")
    private String nume;

    @Column(name = "Descriere")
    private String descriere;

    @Column(name = "Categorie")
    private String categorie;


    public int getCaracteristicaID() {
        return caracteristicaID;
    }

    public void setCaracteristicaID(int caracteristicaID) {
        this.caracteristicaID = caracteristicaID;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
