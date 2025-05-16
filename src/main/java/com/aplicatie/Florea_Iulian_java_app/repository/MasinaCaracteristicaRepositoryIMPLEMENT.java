package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.MasinaCaracteristica;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MasinaCaracteristicaRepositoryIMPLEMENT implements MasinaCaracteristicaRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<MasinaCaracteristica> getMasinaCaracteristici() {
        String query = "SELECT * FROM dbo.Masini_Caracteristici";
        return entityManager.createNativeQuery(query, MasinaCaracteristica.class).getResultList();
    }


    @Override
    public List<MasinaCaracteristica> getCaracteristiciByMasinaId(int masinaID) {

        String query = "SELECT * FROM dbo.Masini_Caracteristici WHERE MasinaID = :masinaID";


        List<Object[]> result = entityManager.createNativeQuery(query)
                .setParameter("masinaID", masinaID)
                .getResultList();


        List<MasinaCaracteristica> masinaCaracteristici = new ArrayList<>();


        for (Object[] row : result) {
            MasinaCaracteristica masinaCaracteristica = new MasinaCaracteristica();
            masinaCaracteristica.setMasinaID((Integer) row[0]);
            masinaCaracteristica.setCaracteristicaID((Integer) row[1]);
            masinaCaracteristici.add(masinaCaracteristica);
        }

        return masinaCaracteristici;
    }




    @Transactional
    @Override
    public void save(MasinaCaracteristica masinaCaracteristica) {
        String query = "INSERT INTO dbo.Masini_Caracteristici (MasinaID, CaracteristicaID) " +
                "VALUES (:masinaID, :caracteristicaID)";
        entityManager.createNativeQuery(query)
                .setParameter("masinaID", masinaCaracteristica.getMasinaID())
                .setParameter("caracteristicaID", masinaCaracteristica.getCaracteristicaID())
                .executeUpdate();
    }

    @Transactional
    @Override
    public void deleteByMasinaId(int masinaId) {
        String query = "DELETE FROM dbo.Masini_Caracteristici WHERE MasinaID = :masinaId";
        entityManager.createNativeQuery(query)
                .setParameter("masinaId", masinaId)
                .executeUpdate();
    }


    @Transactional
    @Override
    public void deleteCaracteristica(int masinaID, int caracteristicaID) {
        String query = "DELETE FROM dbo.Masini_Caracteristici WHERE MasinaID = :masinaID AND CaracteristicaID = :caracteristicaID";
        entityManager.createNativeQuery(query)
                .setParameter("masinaID", masinaID)
                .setParameter("caracteristicaID", caracteristicaID)
                .executeUpdate();
    }

    @Override
    public boolean existsByMasinaIdAndCaracteristicaId(int masinaID, int caracteristicaID) {
        String query = "SELECT COUNT(*) FROM dbo.Masini_Caracteristici WHERE MasinaID = :masinaID AND CaracteristicaID = :caracteristicaID";
        Long count = (Long) entityManager.createNativeQuery(query)
                .setParameter("masinaID", masinaID)
                .setParameter("caracteristicaID", caracteristicaID)
                .getSingleResult();
        return count > 0;
    }



}
