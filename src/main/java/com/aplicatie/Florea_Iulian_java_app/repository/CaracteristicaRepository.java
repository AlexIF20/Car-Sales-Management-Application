package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Caracteristica;

import java.util.List;

public interface CaracteristicaRepository {
    List<Caracteristica> getCaracteristici();
    Caracteristica getCaracteristica(int id);
    void save(Caracteristica caracteristica);
    void delete(int id);
}
