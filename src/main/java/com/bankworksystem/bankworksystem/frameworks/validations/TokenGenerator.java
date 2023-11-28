package com.bankworksystem.bankworksystem.frameworks.validations;

import com.bankworksystem.bankworksystem.useCases.ClientRepository;
import com.bankworksystem.bankworksystem.useCases.Token;
import com.bankworksystem.bankworksystem.useCases.TokenAuthenticationService;

import java.util.*;

public class TokenGenerator implements TokenAuthenticationService {

    Map<String, String> idPassword;

    Set<String> ids;

    private TokenAuthenticator tokenAuthenticator;

    private ClientRepository clientRepository;

    public TokenGenerator(TokenAuthenticator tokenAuthenticator, ClientRepository clientRepository) {
        this.tokenAuthenticator = tokenAuthenticator;
        this.clientRepository = clientRepository;
        clientRepository.setChangeListener(this::onRepositoryChange);
        idPassword = new HashMap<>();
        ids = new HashSet<>();
        reloadRepository();
    }

    public Token getToken(String password) {
        if (idPassword.containsKey(password)) {
            Token token = new Token(idPassword.get(password), password + generateKey());
            tokenAuthenticator.addToken(token);
            return token;
        } else
            throw new IllegalArgumentException("Invalid password");
    }

    public void destroy(Token token) {
        tokenAuthenticator.deleteToken(token);
    }

    public boolean validate(Token token) {
        return tokenAuthenticator.validateToken(token);
    }

    private void onRepositoryChange() {
        reloadRepository();
    }

    private void reloadRepository() {
        idPassword.clear();
        ids.clear();

        clientRepository.getClients().forEach(client -> {
            idPassword.put(client.getPassword(), client.getId());
            ids.add(client.getId());
        });

        tokenAuthenticator.revalidate(ids);
    }

    private String generateKey() {
        StringBuilder keyString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            keyString.append(random.nextInt(10));
            keyString.append(random.nextInt(26) + 'a');
        }

        return keyString.toString();
    }
}
