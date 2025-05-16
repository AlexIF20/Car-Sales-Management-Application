package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Vanzator;

import java.util.List;

public interface VanzatorRepository {
    List<Vanzator> getVanzatori();
    Vanzator getVanzator(int id);
    void save(Vanzator vanzator);
    void update(Vanzator vanzator);
    void delete(int id);
    Vanzator getVanzatorByCNP(String cnp);

}
