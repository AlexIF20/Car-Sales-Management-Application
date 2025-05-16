package com.aplicatie.Florea_Iulian_java_app.repository;

import com.aplicatie.Florea_Iulian_java_app.model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> getClienti();
    Client getClient(int id);
    void save(Client client);
    void update(Client client);
    void delete(int id);
    Client getClientByCNP(String cnp);
}
