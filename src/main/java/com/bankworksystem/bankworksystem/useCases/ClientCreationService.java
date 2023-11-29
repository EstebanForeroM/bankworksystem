package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Gender;

import java.util.HashSet;
import java.util.Set;

public class ClientCreationService {

    private ClientRepository clientRepository;
    private PasswordManager passwordManager;
    Set<String> ids;

    public ClientCreationService(ClientRepository clientRepository, PasswordManager passwordManager) {
        this.clientRepository = clientRepository;
        this.passwordManager = passwordManager;
        ids = new HashSet<String>();
    }

    /*
     * @return id of the created client
     */
    public void createClient(String name, String password, Gender gender, String clientId) {
        validateParametersNotNull(clientId, name, gender, password);
        comproveId(clientId);
        passwordManager.validatePassword(password);
        Client client = new Client(clientId, name, gender, password);
        clientRepository.saveClient(client);
        ids.add(clientId);
    }

    private void validateParametersNotNull(Object... parameters) {
        for (Object parameter : parameters) {
            if (parameter == null) {
                throw new RuntimeException("Parameters cannot be null");
            }
        }
    }

    private void comproveId(String clientId) {
        if (ids.contains(clientId)) {
            throw new RuntimeException("Id already exists");
        }
    }

}
