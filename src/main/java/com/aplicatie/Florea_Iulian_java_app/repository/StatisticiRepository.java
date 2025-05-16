package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Vanzare;

import java.util.List;

public interface StatisticiRepository {

    List<Object[]> getClientiMasiniPeInterval(String dataInceput, String dataSfarsit);
    List<Object[]> getTopMarciVandute();
    List<Object[]> getTopModeleVandute();
    List<Object[]> getTopVanzatori();
    List<Object[]> getTopVanzatoriPeInterval(String dataInceput, String dataSfarsit);
    List<Object[]> getVenituriSiNumarMasiniPerVanzator();

    List<Object[]> getCelMaiProfitabilModelPentruFiecareVanzator();
    List<Object[]> getClientiFaniMarca(String marca);
    List<Object[]> getClientiCuMasiniPesteMediaPreturilor();
    List<String> getDistinctMetodePlata();
    List<Object[]> getClientiByMetodaPlata(String metodaPlata);
    List<Object[]> getTop3LuniCuCeleMaiMulteVanzari();


}
