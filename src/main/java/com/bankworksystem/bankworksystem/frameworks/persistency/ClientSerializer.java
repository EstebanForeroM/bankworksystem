package com.bankworksystem.bankworksystem.frameworks.persistency;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Gender;

import java.util.Objects;

public class ClientSerializer implements Serializer<Client> {
    public String serialize(Client client) {
        Objects.requireNonNull(client, "Client cannot be null");

        return client.getName() + "," + client.getId() + "," + client.getGender().getGenderName() + ","
                + client.getPassword();
    }

    public Client deserialize(String clientString) {
        String[] clientData = clientString.split(",");
        Client client = new Client(clientData[1], clientData[0], Gender.getGenderByName(clientData[2]),
                clientData[3]);
        return client;
    }
}
