package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Marca;

import java.util.List;

public interface MarcaRepository {
    List<Marca> getAllMarci();
    Marca getMarcaById(int id);
    String getMarcaNumeByModelId(int modelId);
}
