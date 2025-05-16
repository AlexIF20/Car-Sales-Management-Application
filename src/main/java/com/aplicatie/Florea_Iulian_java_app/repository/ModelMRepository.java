package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.ModelM;
import java.util.List;

public interface ModelMRepository {
    List<ModelM> getAllModels();
    ModelM getModelById(int id);
}
