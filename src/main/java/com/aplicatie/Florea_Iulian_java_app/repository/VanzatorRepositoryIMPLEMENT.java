package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Client;
import com.aplicatie.Florea_Iulian_java_app.model.Vanzator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VanzatorRepositoryIMPLEMENT implements VanzatorRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Vanzator> getVanzatori() {
        String query = "SELECT * FROM dbo.Vanzatori";
        return entityManager.createNativeQuery(query, Vanzator.class).getResultList();
    }

    @Override
    public Vanzator getVanzator(int id) {
        String query = "SELECT * FROM dbo.Vanzatori WHERE VanzatorID = :id";
        return (Vanzator) entityManager.createNativeQuery(query, Vanzator.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    @Override
    public void save(Vanzator vanzator) {
        String query = "INSERT INTO dbo.Vanzatori (Nume, Prenume, EMail, Telefon, CNP) " +
                "VALUES (:nume, :prenume, :email, :telefon, :cnp)";
        entityManager.createNativeQuery(query)
                .setParameter("nume", vanzator.getNume())
                .setParameter("prenume", vanzator.getPrenume())
                .setParameter("email", vanzator.getEmail())
                .setParameter("telefon", vanzator.getTelefon())
                .setParameter("cnp", vanzator.getCnp())
                .executeUpdate();
    }


    @Transactional
    @Override
    public void update(Vanzator vanzator) {
        String query = "UPDATE dbo.Vanzatori SET Nume = :nume, Prenume = :prenume, EMail = :email, Telefon = :telefon, CNP = :cnp WHERE VanzatorID = :id";
        entityManager.createNativeQuery(query)
                .setParameter("nume", vanzator.getNume())
                .setParameter("prenume", vanzator.getPrenume())
                .setParameter("email", vanzator.getEmail())
                .setParameter("telefon", vanzator.getTelefon())
                .setParameter("cnp", vanzator.getCnp())
                .setParameter("id", vanzator.getVanzatorID())
                .executeUpdate();
    }


    @Transactional
    @Override
    public void delete(int id) {
        String query = "DELETE FROM dbo.Vanzatori WHERE VanzatorID = :id";
        entityManager.createNativeQuery(query)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Vanzator getVanzatorByCNP(String cnp) {
        try {
            return entityManager.createQuery("SELECT v FROM Vanzator v WHERE v.cnp = :cnp", Vanzator.class)
                    .setParameter("cnp", cnp)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
