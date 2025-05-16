package com.aplicatie.Florea_Iulian_java_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Vanzatori", schema = "dbo")
public class Vanzator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VanzatorID")
    private int vanzatorID;

    @Column(name = "Nume")
    private String nume;

    @Column(name = "Prenume")
    private String prenume;

    @Column(name = "EMail")
    private String email;

    @Column(name = "Telefon", length = 10)
    private String telefon;

    @Column(name = "CNP", length = 13)
    private String cnp;


    public int getVanzatorID() {
        return vanzatorID;
    }

    public void setVanzatorID(int vanzatorID) {
        this.vanzatorID = vanzatorID;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }
}
