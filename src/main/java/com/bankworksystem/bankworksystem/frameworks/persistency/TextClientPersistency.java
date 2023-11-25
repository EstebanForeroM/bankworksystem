package com.bankworksystem.bankworksystem.frameworks.persistency;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.useCases.ClientRepository;

import java.util.Set;

public class TextClientPersistency implements ClientRepository {

    private TextPersistency<Client> textPersistency;

    public TextClientPersistency(String filePath, Serializer<Client> serializer) {
        textPersistency = new TextPersistency<>(filePath, "clients.txt", serializer);
    }

    @Override
    public void saveClient(Client client) {
        textPersistency.saveObject(client);
    }

    @Override
    public Client getClient(String id) {
        return textPersistency.getObject(id);
    }

    @Override
    public void deleteClient(String id) {
        textPersistency.deleteObject(id);
    }

    @Override
    public void updateClient(String id, Client client) {
        textPersistency.updateObject(id, client);
    }

    @Override
    public Set<Client> getClients() {
        return textPersistency.getObjects();
    }

    @Override
    public void setChangeListener(Runnable callback) {
        textPersistency.addChangeListener(callback);
    }

    public void eraseAll() {
        textPersistency.eraseAll();
    }
}
