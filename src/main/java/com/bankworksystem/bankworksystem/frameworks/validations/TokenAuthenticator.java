package com.bankworksystem.bankworksystem.frameworks.validations;

import com.bankworksystem.bankworksystem.useCases.Token;

import java.util.Set;

public interface TokenAuthenticator {

    boolean validateToken(Token token);

    void addToken(Token token);

    void deleteToken(Token token);

    public void revalidate(Set<String> ids);
}
