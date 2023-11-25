package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Gender;
import com.bankworksystem.bankworksystem.entities.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientSearcher {

    ClientRepository clientRepository;

    Set<Client> clients;

    public ClientSearcher(ClientRepository clientRepository) {
        clients = new HashSet<>();
        this.clientRepository = clientRepository;
        clientRepository.setChangeListener(this::onRepositoryChange);
        reloadDTO();
    }

    private void onRepositoryChange() {
        reloadDTO();
    }

    private void reloadDTO() {
        clients = clientRepository.getClients();
    }

    public Set<Client> getClientsFromProducts(Set<Product> products) {
        Set<Client> clients = new HashSet<>();

        for (Product product : products) {
            clients.add(getClientById(product.getOwnerId()));
        }

        return clients;
    }

    public List<Client> getClients() {

        List<Client> clients = new ArrayList<>();

        for (Client client : this.clients) {
            clients.add(cloneClient(client));
        }

        return clients;
    }

    public Client getClientById(String id) {
        for (Client client : clients) {
            if (client.getId().equals(id)) {
                return cloneClient(client);
            }
        }
        throw new RuntimeException("Client not found");
    }

    public Client getClientByProduct(Product product) {
        for (Client client : clients) {
            if (client.getId() == product.getOwnerId()) {
                return cloneClient(client);
            }
        }
        throw new RuntimeException("Client not found");
    }

    public Set<Client> getClientsById(String id) {
        Set<Client> clientsById = new HashSet<>();

        for (Client client : clients) {
            if (client.getId().contains(id))
                clientsById.add(cloneClient(client));
        }

        return clientsById;
    }

    public boolean userExists(String id) {
        return !getClientsById(id).isEmpty();
    }

    public Set<Client> getClientsByName(String name) {
        Set<Client> clientsByName = new HashSet<>();

        for (Client client : clients) {
            if (client.getName().contains(name))
                clientsByName.add(cloneClient(client));
        }

        return clientsByName;
    }

    public Set<Client> getClientsByGender(Gender gender) {
        Set<Client> clientsByGender = new HashSet<>();

        for (Client client : clients) {
            if (client.getGender().equals(gender))
                clientsByGender.add(cloneClient(client));
        }

        return clientsByGender;
    }

    private Client cloneClient(Client client) {
        return new Client(client.getId(), client.getName(), client.getGender(), client.getPassword());
    }
}
