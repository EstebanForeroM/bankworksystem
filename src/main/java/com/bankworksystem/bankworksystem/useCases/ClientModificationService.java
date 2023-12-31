package com.bankworksystem.bankworksystem.useCases;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Gender;

public class ClientModificationService {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final ClientRepository clientRepository;
    private final PasswordManager passwordManager;

    public ClientModificationService(TokenAuthenticationService tokenAuthenticationService,
                                     ClientRepository clientRepository,
                                     PasswordManager passwordManager) {

        this.tokenAuthenticationService = tokenAuthenticationService;
        this.clientRepository = clientRepository;
        this.passwordManager = passwordManager;
    }

    public void modifyUserName(Token token, String name) {
        userModification(token, name, null, null);
    }

    public void modifyUserPassword(Token token, String passWord) {
        passwordManager.validatePassword(passWord);
        userModification(token, null, passWord, null);
    }

    public void modifyUserGender(Token token, Gender gender) {
        userModification(token, null, null, gender);
    }

    public void modifyUser(Token token, String name, String password, Gender gender) {
        passwordManager.validatePassword(password);
        userModification(token, name, password, gender);
    }

    private void userModification(Token token, String name, String password, Gender gender) {
        validateToken(token);
        Client client = clientRepository.getClient(token.getClientId());
        if (name != null)
            client.setName(name);
        if (password != null)
            client.setPassword(password);
        if (gender != null)
            client.setGender(gender);
        clientRepository.updateClient(token.getClientId(), client);
    }

    private void validateToken(Token token) {
        if (!tokenAuthenticationService.validate(token)) {
            throw new RuntimeException("Authentication failed: Invalid token");
        }
    }
}
