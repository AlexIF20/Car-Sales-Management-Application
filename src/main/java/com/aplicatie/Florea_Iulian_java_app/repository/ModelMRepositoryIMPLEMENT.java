package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.ModelM;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public class ModelMRepositoryIMPLEMENT implements ModelMRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ModelM> getAllModels() {
        String query = "SELECT * FROM dbo.Model";
        return entityManager.createNativeQuery(query, ModelM.class).getResultList();
    }

    @Override
    public ModelM getModelById(int id) {
        String query = "SELECT * FROM dbo.Model WHERE ModelID = :id";
        return (ModelM) entityManager.createNativeQuery(query, ModelM.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
