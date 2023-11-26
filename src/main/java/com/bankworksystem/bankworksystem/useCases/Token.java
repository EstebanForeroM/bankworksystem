package com.bankworksystem.bankworksystem.useCases;

public class Token {
    private String clientId;
    private String key;

    public Token(String clientId, String key) {
        this.clientId = clientId;
        this.key = key;
    }

    public String getClientId() {
        return clientId;
    }

    public String getKey() {
        return key;
    }
}
