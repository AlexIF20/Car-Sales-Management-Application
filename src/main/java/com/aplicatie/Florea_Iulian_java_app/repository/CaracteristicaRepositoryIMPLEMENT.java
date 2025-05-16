package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Caracteristica;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CaracteristicaRepositoryIMPLEMENT implements CaracteristicaRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Caracteristica> getCaracteristici() {
        return entityManager.createQuery("SELECT c FROM Caracteristica c", Caracteristica.class).getResultList();
    }

    @Override
    public Caracteristica getCaracteristica(int id) {
        String query = "SELECT * FROM Caracteristici WHERE CaracteristicaID = :id";
        return (Caracteristica) entityManager.createNativeQuery(query, Caracteristica.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    @Override
    public void save(Caracteristica caracteristica) {
        if (caracteristica.getCaracteristicaID() == 0) {
            String query = """
                INSERT INTO dbo.Caracteristici (Nume) 
                VALUES (:nume)
            """;
            entityManager.createNativeQuery(query)
                    .setParameter("nume", caracteristica.getNume())
                    .executeUpdate();
        } else {
            String query = """
                UPDATE dbo.Caracteristici 
                SET Nume = :nume 
                WHERE CaracteristicaID = :id
            """;
            entityManager.createNativeQuery(query)
                    .setParameter("nume", caracteristica.getNume())
                    .setParameter("id", caracteristica.getCaracteristicaID())
                    .executeUpdate();
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        String query = """
            DELETE FROM dbo.Caracteristici 
            WHERE CaracteristicaID = :id
        """;
        entityManager.createNativeQuery(query)
                .setParameter("id", id)
                .executeUpdate();
    }
}
