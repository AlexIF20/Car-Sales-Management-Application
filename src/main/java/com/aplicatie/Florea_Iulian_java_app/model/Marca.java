package com.aplicatie.Florea_Iulian_java_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Marca", schema = "dbo")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MarcaID")
    private int marcaID;

    @Column(name = "Nume")
    private String nume;


    public int getMarcaID() {
        return marcaID;
    }

    public void setMarcaID(int marcaID) {
        this.marcaID = marcaID;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
