package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Marca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MarcaRepositoryIMPLEMENT implements MarcaRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Marca> getAllMarci() {
        String query = "SELECT * FROM dbo.Marca";
        return entityManager.createNativeQuery(query, Marca.class).getResultList();
    }

    @Override
    public Marca getMarcaById(int marcaID) {
        try {
            String query = "SELECT * FROM dbo.Marca WHERE MarcaID = :marcaID";
            return (Marca) entityManager.createNativeQuery(query, Marca.class)
                    .setParameter("marcaID", marcaID)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public String getMarcaNumeByModelId(int modelId) {
        String query = """
                SELECT m.Nume 
                FROM dbo.Model mm
                JOIN dbo.Marca m ON mm.MarcaID = m.MarcaID
                WHERE mm.ModelID = :modelId
                """;
        return (String) entityManager.createNativeQuery(query)
                .setParameter("modelId", modelId)
                .getSingleResult();
    }
}
