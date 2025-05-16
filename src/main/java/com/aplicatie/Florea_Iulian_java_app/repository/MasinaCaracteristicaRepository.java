package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.MasinaCaracteristica;
import java.util.List;

public interface MasinaCaracteristicaRepository {
    List<MasinaCaracteristica> getMasinaCaracteristici();
    List<MasinaCaracteristica> getCaracteristiciByMasinaId(int masinaId);
    void save(MasinaCaracteristica masinaCaracteristica);
    void deleteByMasinaId(int masinaId);

    void deleteCaracteristica(int masinaID, int caracteristicaID);
    boolean existsByMasinaIdAndCaracteristicaId(int masinaID, int caracteristicaID);

}
