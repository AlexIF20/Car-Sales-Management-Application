package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Masina;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public class MasinaRepositoryIMPLEMENT implements MasinaRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Masina> getMasini() {
        String query = "SELECT * FROM dbo.Masini";
        return entityManager.createNativeQuery(query, Masina.class).getResultList();
    }

    @Override
    public Masina getMasini(int id) {
        String query = "SELECT * FROM dbo.Masini WHERE MasinaID = :id";
        return (Masina) entityManager.createNativeQuery(query, Masina.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    @Override
    public void save(Masina masina) {
        String query = "INSERT INTO dbo.Masini (ModelID, An_Fabricatie, VIN, Vanduta) " +
                "VALUES (:modelID, :anFabricatie, :vin, :vanduta)";
        entityManager.createNativeQuery(query)
                .setParameter("modelID", masina.getModelID())
                .setParameter("anFabricatie", masina.getAnFabricatie())
                .setParameter("vin", masina.getVin())
                .setParameter("vanduta", masina.getVanduta())
                .executeUpdate();
    }

    @Transactional
    @Override
    public void updateMasina(Masina masina) {
        String query = "UPDATE dbo.Masini " +
                "SET ModelID = :modelID, An_Fabricatie = :anFabricatie, Vanduta = :vanduta " +
                "WHERE VIN = :vin";
        entityManager.createNativeQuery(query)
                .setParameter("modelID", masina.getModelID())
                .setParameter("anFabricatie", masina.getAnFabricatie())
                .setParameter("vin", masina.getVin())
                .setParameter("vanduta", masina.getVanduta())
                .executeUpdate();
    }




    @Transactional
    @Override
    public void delete(int id) {
        String query = "DELETE FROM dbo.Masini WHERE MasinaID = :id";
        entityManager.createNativeQuery(query)
                .setParameter("id", id)
                .executeUpdate();
    }



}
