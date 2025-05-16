package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Vanzare;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;

import java.util.List;
import java.util.Locale;

@Repository
public class VanzareRepositoryIMPLEMENT implements VanzareRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Vanzare> getVanzari() {
        String query = "SELECT * FROM dbo.Vanzari";
        return entityManager.createNativeQuery(query, Vanzare.class).getResultList();
    }

    @Override
    public Vanzare getVanzare(int id) {
        String query = "SELECT * FROM dbo.Vanzari WHERE VanzareID = :id";
        return (Vanzare) entityManager.createNativeQuery(query, Vanzare.class)
                .setParameter("id", id)
                .getSingleResult();
    }


    @Override
    public List<Object[]> getVanzariDetaliate() {
        String query = """
        SELECT 
            v.VanzareID,
            CONCAT(c.Nume, ' ', c.Prenume) AS ClientNumeComplet,
            CONCAT(vz.Nume, ' ', vz.Prenume) AS VanzatorNumeComplet,
            mrc.Nume AS MarcaNume,
            mdl.Nume AS ModelNume,
            ms.VIN AS VIN,
            v.Pret_Vanzare,
            v.Metoda_Plata,
            v.Data_Vanzarii
        FROM 
            dbo.Vanzari v
        JOIN dbo.Clienti c ON v.ClientID = c.ClientID
        JOIN dbo.Vanzatori vz ON v.VanzatorID = vz.VanzatorID
        JOIN dbo.Masini ms ON v.MasinaID = ms.MasinaID
        JOIN dbo.Model mdl ON ms.ModelID = mdl.ModelID
        JOIN dbo.Marca mrc ON mdl.MarcaID = mrc.MarcaID
    """;

        List<Object[]> results = entityManager.createNativeQuery(query).getResultList();

        DecimalFormat df = new DecimalFormat("#,###.00");
        for (Object[] result : results) {
            result[6] = df.format(result[6]); // Formatăm Pret_Vanzare (al șaptelea element din rezultat)
        }

        return results;
    }



    @Transactional
    @Override
    public void save(Vanzare vanzare) {
        if (vanzare.getVanzareID() == 0) {

            String insertQuery = """
            INSERT INTO dbo.Vanzari (ClientID, VanzatorID, MasinaID, Pret_Vanzare, Metoda_Plata, Data_Vanzarii)
            VALUES (:clientID, :vanzatorID, :masinaID, :pretVanzare, :metodaPlata, :dataVanzarii)
        """;
            entityManager.createNativeQuery(insertQuery)
                    .setParameter("clientID", vanzare.getClientID())
                    .setParameter("vanzatorID", vanzare.getVanzatorID())
                    .setParameter("masinaID", vanzare.getMasinaID())
                    .setParameter("pretVanzare", vanzare.getPretVanzare())
                    .setParameter("metodaPlata", vanzare.getMetodaPlata())
                    .setParameter("dataVanzarii", vanzare.getDataVanzarii())
                    .executeUpdate();
        } else {

            String updateQuery = """
            UPDATE dbo.Vanzari
            SET 
                ClientID = :clientID,
                VanzatorID = :vanzatorID,
                MasinaID = :masinaID,
                Pret_Vanzare = :pretVanzare,
                Metoda_Plata = :metodaPlata,
                Data_Vanzarii = :dataVanzarii
            WHERE VanzareID = :vanzareID
        """;
            entityManager.createNativeQuery(updateQuery)
                    .setParameter("clientID", vanzare.getClientID())
                    .setParameter("vanzatorID", vanzare.getVanzatorID())
                    .setParameter("masinaID", vanzare.getMasinaID())
                    .setParameter("pretVanzare", vanzare.getPretVanzare())
                    .setParameter("metodaPlata", vanzare.getMetodaPlata())
                    .setParameter("dataVanzarii", vanzare.getDataVanzarii())
                    .setParameter("vanzareID", vanzare.getVanzareID())
                    .executeUpdate();
        }
    }


    @Transactional
    @Override
    public void delete(int id) {
        String query = "DELETE FROM dbo.Vanzari WHERE VanzareID = :id";
        entityManager.createNativeQuery(query)
                .setParameter("id", id)
                .executeUpdate();
    }



}
