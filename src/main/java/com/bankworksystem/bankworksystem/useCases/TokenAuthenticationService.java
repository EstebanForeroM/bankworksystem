package com.bankworksystem.bankworksystem.useCases;

public interface TokenAuthenticationService {

    public Token getToken(String password);

    public void destroy(Token token);

    public boolean validate(Token token);
}
