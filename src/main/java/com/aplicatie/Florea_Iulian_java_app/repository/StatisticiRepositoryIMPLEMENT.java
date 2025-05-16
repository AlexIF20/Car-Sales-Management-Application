package com.aplicatie.Florea_Iulian_java_app.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;


@Repository
@Transactional
public class StatisticiRepositoryIMPLEMENT implements StatisticiRepository {

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<Object[]> getClientiMasiniPeInterval(String dataInceput, String dataSfarsit) {
        String query = """
            SELECT c.Nume, c.Prenume, m.Nume AS Marca, md.Nume AS Model, v.Data_Vanzarii
            FROM dbo.Vanzari v
            JOIN dbo.Clienti c ON v.ClientID = c.ClientID
            JOIN dbo.Masini ms ON v.MasinaID = ms.MasinaID
            JOIN dbo.Model md ON ms.ModelID = md.ModelID
            JOIN dbo.Marca m ON md.MarcaID = m.MarcaID
            WHERE v.Data_Vanzarii BETWEEN :dataInceput AND :dataSfarsit
            """;
        return entityManager.createNativeQuery(query)
                .setParameter("dataInceput", dataInceput)
                .setParameter("dataSfarsit", dataSfarsit)
                .getResultList();
    }


    @Override
    public List<Object[]> getTopMarciVandute() {
        String query = """
        SELECT m.Nume AS Marca, COUNT(*) AS NumarVanzari
        FROM dbo.Vanzari v
        JOIN dbo.Masini ms ON v.MasinaID = ms.MasinaID
        JOIN dbo.Model md ON ms.ModelID = md.ModelID
        JOIN dbo.Marca m ON md.MarcaID = m.MarcaID
        GROUP BY m.Nume
        ORDER BY NumarVanzari DESC
    """;
        return entityManager.createNativeQuery(query).getResultList();
    }

    @Override
    public List<Object[]> getTopModeleVandute() {
        String query = """
        SELECT CONCAT('(', m.Nume, ') ', md.Nume) AS ModelCompleto, COUNT(*) AS NumarVanzari
        FROM dbo.Vanzari v
        JOIN dbo.Masini ms ON v.MasinaID = ms.MasinaID
        JOIN dbo.Model md ON ms.ModelID = md.ModelID
        JOIN dbo.Marca m ON md.MarcaID = m.MarcaID
        GROUP BY m.Nume, md.Nume
        ORDER BY NumarVanzari DESC
    """;
        return entityManager.createNativeQuery(query).getResultList();
    }



    @Override
    public List<Object[]> getTopVanzatori() {
        String query = """
        SELECT TOP 5 CONCAT(z.Nume, ' ', z.Prenume) AS NumeVanzator, COUNT(*) AS NumarVanzari
        FROM dbo.Vanzari v
        JOIN dbo.Vanzatori z ON v.VanzatorID = z.VanzatorID
        GROUP BY z.Nume, z.Prenume
        ORDER BY NumarVanzari DESC
    """;
        return entityManager.createNativeQuery(query).getResultList();
    }

    @Override
    public List<Object[]> getTopVanzatoriPeInterval(String dataInceput, String dataSfarsit) {
        String query = """
        SELECT TOP 5 CONCAT(z.Nume, ' ', z.Prenume) AS NumeVanzator, COUNT(*) AS NumarVanzari
        FROM dbo.Vanzari v
        JOIN dbo.Vanzatori z ON v.VanzatorID = z.VanzatorID
        WHERE v.Data_Vanzarii BETWEEN :dataInceput AND :dataSfarsit
        GROUP BY z.Nume, z.Prenume
        ORDER BY NumarVanzari DESC
    """;
        return entityManager.createNativeQuery(query)
                .setParameter("dataInceput", dataInceput)
                .setParameter("dataSfarsit", dataSfarsit)
                .getResultList();
    }


    public List<Object[]> getVenituriSiNumarMasiniPerVanzator() {
        String query = """
        SELECT 
            CONCAT(vz.Nume, ' ', vz.Prenume) AS Vanzator,
            COUNT(v.MasinaID) AS NumarMasiniVandute,
            SUM(v.Pret_vanzare) AS TotalVenituri
        FROM dbo.Vanzari v
        JOIN dbo.Vanzatori vz ON v.VanzatorID = vz.VanzatorID
        GROUP BY vz.Nume, vz.Prenume
        ORDER BY TotalVenituri DESC
    """;
        List<Object[]> results = entityManager.createNativeQuery(query).getResultList();

        DecimalFormat df = new DecimalFormat("#,###.00");
        for (Object[] result : results) {
            result[2] = df.format(result[2]);
        }
        return results;
    }


    @Override    //Subcerere
    public List<Object[]> getCelMaiProfitabilModelPentruFiecareVanzator() {
        String query = """
        WITH ProfitPerModel AS (
            SELECT 
                v.VanzatorID,
                CONCAT(mrc.Nume, ' ', mdl.Nume) AS Model,
                SUM(v.Pret_Vanzare) AS TotalVenituri,
                COUNT(v.MasinaID) AS NumarModeleVandute
            FROM dbo.Vanzari v
            JOIN dbo.Masini ms ON v.MasinaID = ms.MasinaID
            JOIN dbo.Model mdl ON ms.ModelID = mdl.ModelID
            JOIN dbo.Marca mrc ON mdl.MarcaID = mrc.MarcaID
            GROUP BY v.VanzatorID, mrc.Nume, mdl.Nume
        ),
        MaxProfitPerVanzator AS (
            SELECT 
                pp.VanzatorID,
                MAX(pp.TotalVenituri) AS MaxVenituri
            FROM ProfitPerModel pp
            GROUP BY pp.VanzatorID
        )
        SELECT 
            CONCAT(vz.Nume, ' ', vz.Prenume) AS Vanzator,
            pm.Model,
            pm.TotalVenituri,
            pm.NumarModeleVandute
        FROM ProfitPerModel pm
        JOIN MaxProfitPerVanzator mp ON pm.VanzatorID = mp.VanzatorID AND pm.TotalVenituri = mp.MaxVenituri
        JOIN dbo.Vanzatori vz ON pm.VanzatorID = vz.VanzatorID
        ORDER BY Vanzator, TotalVenituri DESC
    """;

        List<Object[]> results = entityManager.createNativeQuery(query).getResultList();


        DecimalFormat df = new DecimalFormat("#,###.00");
        for (Object[] result : results) {
            result[2] = df.format(result[2]);
        }

        return results;
    }




    @Override   //Subcerere
    public List<Object[]> getClientiFaniMarca(String marca) {
        String query = """
    SELECT 
        CONCAT(c.Nume, ' ', c.Prenume) AS NumeClient, 
        COUNT(v.VanzareID) AS NumarVanzari
    FROM 
        dbo.Vanzari v
    JOIN 
        dbo.Clienti c ON v.ClientID = c.ClientID
    JOIN 
        dbo.Masini ms ON v.MasinaID = ms.MasinaID
    JOIN 
        dbo.Model md ON ms.ModelID = md.ModelID
    JOIN 
        dbo.Marca m ON md.MarcaID = m.MarcaID
    WHERE 
        m.Nume = :marca
    GROUP BY 
        c.ClientID, c.Nume, c.Prenume
    HAVING 
        COUNT(v.VanzareID) >= (
            SELECT ISNULL(MAX(cnt), 0)
            FROM (
                SELECT COUNT(v2.VanzareID) AS cnt
                FROM dbo.Vanzari v2
                JOIN dbo.Masini ms2 ON v2.MasinaID = ms2.MasinaID
                JOIN dbo.Model md2 ON ms2.ModelID = md2.ModelID
                JOIN dbo.Marca m2 ON md2.MarcaID = m2.MarcaID
                WHERE v2.ClientID = c.ClientID AND m2.Nume != :marca
                GROUP BY m2.Nume
            ) AS subquery
        )
    ORDER BY 
        NumarVanzari DESC
    """;

        return entityManager.createNativeQuery(query)
                .setParameter("marca", marca)
                .getResultList();
    }




    @Override  //Subcerere
    public List<Object[]> getClientiCuMasiniPesteMediaPreturilor() {
        String query = """
    WITH MediaPreturilor AS (
        SELECT AVG(v.Pret_Vanzare) AS MediaPret
        FROM dbo.Vanzari v
    )
    SELECT 
        CONCAT(c.Nume, ' ', c.Prenume) AS NumeClient,
        mp.MediaPret,
        CONCAT(mrc.Nume, ' ', md.Nume) AS ModelMasina,
        v.Pret_Vanzare
    FROM 
        dbo.Vanzari v
    JOIN 
        dbo.Clienti c ON v.ClientID = c.ClientID
    JOIN 
        dbo.Masini ms ON v.MasinaID = ms.MasinaID
    JOIN 
        dbo.Model md ON ms.ModelID = md.ModelID
    JOIN 
        dbo.Marca mrc ON md.MarcaID = mrc.MarcaID
    CROSS JOIN 
        MediaPreturilor mp
    WHERE 
        v.Pret_Vanzare > mp.MediaPret
    ORDER BY 
        NumeClient ASC, v.Pret_Vanzare DESC
    """;

        List<Object[]> results = entityManager.createNativeQuery(query).getResultList();

        DecimalFormat df = new DecimalFormat("#,###.00");
        for (Object[] result : results) {
            result[1] = df.format(result[1]); // MediaPret
            result[3] = df.format(result[3]); // Pret_Vanzare
        }

        return results;
    }


    //Subcerere
    public List<Object[]> getClientiByMetodaPlata(String metodaPlata) {
        String query = """
            WITH MediaPreturilor AS (
                SELECT AVG(v.Pret_Vanzare) AS MediaPret
                FROM Vanzari v
            )
            SELECT 
                CONCAT(c.nume, ' ', c.prenume) AS NumeComplet,
                CONCAT(mrc.nume, ' ', mdl.nume) AS Masina,
                v.Pret_Vanzare AS PretVanzare,
                v.Data_Vanzarii AS DataVanzare,
                (CASE 
                    WHEN v.Pret_Vanzare > (SELECT MediaPret FROM MediaPreturilor) THEN 'Peste Medie'
                    ELSE 'Sub Medie'
                END) AS ClasificarePret
            FROM 
                Vanzari v
            JOIN 
                Masini ms ON v.masinaID = ms.masinaID
            JOIN 
                Model mdl ON ms.modelID = mdl.modelID
            JOIN 
                Marca mrc ON mdl.marcaID = mrc.marcaID
            JOIN 
                Clienti c ON v.clientID = c.clientID
            WHERE 
                v.Metoda_Plata = :metodaPlata
            ORDER BY 
                v.Data_Vanzarii DESC, v.Pret_Vanzare DESC
            """;

        List<Object[]> results = entityManager.createNativeQuery(query)
                .setParameter("metodaPlata", metodaPlata)
                .getResultList();


        DecimalFormat df = new DecimalFormat("#,###.00");
        for (Object[] result : results) {
            result[2] = df.format(result[2]);
        }

        return results;
    }


    public List<String> getDistinctMetodePlata() {
        String query = "SELECT DISTINCT v.Metoda_Plata FROM Vanzari v";
        return entityManager.createNativeQuery(query).getResultList();
    }



    @Override //Subcerere
    public List<Object[]> getTop3LuniCuCeleMaiMulteVanzari() {
        String query = """
        WITH VanzariPeLuna AS (
            SELECT 
                YEAR(v.Data_Vanzarii) AS An, 
                MONTH(v.Data_Vanzarii) AS Luna, 
                COUNT(v.VanzareID) AS NumarVanzari
            FROM dbo.Vanzari v
            GROUP BY YEAR(v.Data_Vanzarii), MONTH(v.Data_Vanzarii)
        ),
        TopLuni AS (
            SELECT 
                An, 
                Luna, 
                NumarVanzari,
                RANK() OVER (PARTITION BY An ORDER BY NumarVanzari DESC) AS Rnk
            FROM VanzariPeLuna
        )
        SELECT 
            An, 
            Luna,
            NumarVanzari
        FROM TopLuni
        WHERE Rnk <= 3
        ORDER BY An DESC, NumarVanzari DESC
    """;

        List<Object[]> results = entityManager.createNativeQuery(query).getResultList();


        Locale locale = new Locale("ro", "RO");
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] luni = symbols.getMonths();

        for (Object[] result : results) {
            int lunaIndex = (int) result[1] - 1;
            String luna = luni[lunaIndex].toLowerCase();

            luna = luna.substring(0, 1).toUpperCase() + luna.substring(1);
            result[1] = luna;
        }

        return results;
    }


}
