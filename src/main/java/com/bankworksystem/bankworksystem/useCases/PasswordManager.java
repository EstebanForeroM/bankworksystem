package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Client;

import java.util.HashSet;
import java.util.Set;

public class PasswordManager {
    Set<String> passwords;
    private ClientRepository clientRepository;

    public PasswordManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        passwords = new HashSet<>();
        clientRepository.setChangeListener(this::OnRepositoryChange);
    }

    private void OnRepositoryChange() {
        passwords.clear();
        Set<Client> clients = clientRepository.getClients();

        for (Client client : clients) {
            passwords.add(client.getPassword());
        }
    }

    public void validatePassword(String password) {
        if (password == null)
            throw new RuntimeException("Password cannot be null");
        if (passwords.contains(password))
            throw new IllegalArgumentException("Password already exists");
    }
}
