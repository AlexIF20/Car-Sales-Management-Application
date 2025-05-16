package com.aplicatie.Florea_Iulian_java_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Model", schema = "dbo")
public class ModelM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ModelID")
    private int modelID;

    @Column(name = "MarcaID") // Legătura către marcă
    private int marcaID;

    @Column(name = "Nume")
    private String numeModel;


    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public int getMarcaID() {
        return marcaID;
    }

    public void setMarcaID(int marcaID) {
        this.marcaID = marcaID;
    }

    public String getNumeModel() {
        return numeModel;
    }

    public void setNumeModel(String numeModel) {
        this.numeModel = numeModel;
    }
}
