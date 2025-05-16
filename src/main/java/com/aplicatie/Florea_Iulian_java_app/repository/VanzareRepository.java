package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Vanzare;

import java.util.List;

public interface VanzareRepository {
    List<Vanzare> getVanzari();
    Vanzare getVanzare(int id);
    List<Object[]> getVanzariDetaliate();
    void save(Vanzare vanzare);
    void delete(int id);

    }
