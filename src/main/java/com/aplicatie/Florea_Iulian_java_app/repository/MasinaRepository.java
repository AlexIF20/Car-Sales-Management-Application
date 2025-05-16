package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Masina;

import java.util.List;

public interface MasinaRepository {
    List<Masina> getMasini();
    Masina getMasini(int id);
    void save(Masina masina);
    void delete(int id);
    void updateMasina(Masina masina);
}
