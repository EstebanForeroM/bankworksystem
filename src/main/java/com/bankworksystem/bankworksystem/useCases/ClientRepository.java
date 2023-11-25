package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Client;

import java.util.Set;

public interface ClientRepository {

    void saveClient(Client client);

    Client getClient(String id);

    void deleteClient(String id);

    void updateClient(String id, Client client);

    Set<Client> getClients();

    void setChangeListener(Runnable callback);
}
