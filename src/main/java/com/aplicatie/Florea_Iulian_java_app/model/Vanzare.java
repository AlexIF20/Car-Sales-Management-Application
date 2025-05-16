package com.aplicatie.Florea_Iulian_java_app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Vanzari", schema = "dbo")
public class Vanzare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VanzareID")
    private int vanzareID;

    @Column(name = "MasinaID")
    private int masinaID;

    @Column(name = "ClientID")
    private int clientID;

    @Column(name = "VanzatorID")
    private int vanzatorID;

    @Column(name = "Data_vanzarii")
    private LocalDateTime dataVanzarii;

    @Column(name = "Pret_vanzare")
    private BigDecimal pretVanzare;

    @Column(name = "Metoda_plata")
    private String metodaPlata;


    public int getVanzareID() {
        return vanzareID;
    }

    public void setVanzareID(int vanzareID) {
        this.vanzareID = vanzareID;
    }

    public int getMasinaID() {
        return masinaID;
    }

    public void setMasinaID(int masinaID) {
        this.masinaID = masinaID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getVanzatorID() {
        return vanzatorID;
    }

    public void setVanzatorID(int vanzatorID) {
        this.vanzatorID = vanzatorID;
    }

    public LocalDateTime getDataVanzarii() {
        return dataVanzarii;
    }

    public void setDataVanzarii(LocalDateTime dataVanzarii) {
        this.dataVanzarii = dataVanzarii;
    }

    public BigDecimal getPretVanzare() {
        return pretVanzare;
    }

    public void setPretVanzare(BigDecimal pretVanzare) {
        this.pretVanzare = pretVanzare;
    }

    public String getMetodaPlata() {
        return metodaPlata;
    }

    public void setMetodaPlata(String metodaPlata) {
        this.metodaPlata = metodaPlata;
    }
}
