package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Client;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public class ClientRepositoryIMPLEMENT implements ClientRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Client> getClienti() {
        String query = "SELECT * FROM dbo.Clienti";
        return entityManager.createNativeQuery(query, Client.class).getResultList();
    }

    @Override
    public Client getClient(int id) {
        String query = "SELECT * FROM dbo.Clienti WHERE ClientID = :id";
        return (Client) entityManager.createNativeQuery(query, Client.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    @Override
    public void save(Client client) {
        String query = "INSERT INTO dbo.Clienti (Nume, Prenume, EMail, Adresa, Telefon, CNP) " +
                "VALUES (:nume, :prenume, :email, :adresa, :telefon, :cnp)";
        entityManager.createNativeQuery(query)
                .setParameter("nume", client.getNume())
                .setParameter("prenume", client.getPrenume())
                .setParameter("email", client.getEmail())
                .setParameter("adresa", client.getAdresa())
                .setParameter("telefon", client.getTelefon())
                .setParameter("cnp", client.getCnp())
                .executeUpdate();
    }

    @Transactional
    @Override
    public void update(Client client) {
        String query = "UPDATE dbo.Clienti SET Nume = :nume, Prenume = :prenume, EMail = :email, Adresa = :adresa, Telefon = :telefon, CNP = :cnp WHERE ClientID = :id";
        entityManager.createNativeQuery(query)
                .setParameter("nume", client.getNume())
                .setParameter("prenume", client.getPrenume())
                .setParameter("email", client.getEmail())
                .setParameter("adresa", client.getAdresa())
                .setParameter("telefon", client.getTelefon())
                .setParameter("cnp", client.getCnp())
                .setParameter("id", client.getClientID())
                .executeUpdate();
    }


    @Transactional
    @Override
    public void delete(int id) {
        String query = "DELETE FROM dbo.Clienti WHERE ClientID = :id";
        entityManager.createNativeQuery(query)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public Client getClientByCNP(String cnp) {
        try {
            return entityManager.createQuery("SELECT c FROM Client c WHERE c.cnp = :cnp", Client.class)
                    .setParameter("cnp", cnp)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
