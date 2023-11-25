package com.bankworksystem.bankworksystem.entities;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void setId() {
        Client client = generateDefaultClient();

        client.setId("123456789");

        assertThrows(IllegalArgumentException.class, () -> {
            client.setId("1234567890e");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            client.setId("1234567890,");
        });

        assertDoesNotThrow(() -> {
            client.setId("1234567890 ");
        });

        assert client.getId().equals("1234567890");
    }

    @Test
    void setName() {
        Client client = generateDefaultClient();

        assertThrows(IllegalArgumentException.class, () -> {
            client.setName(" ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            client.setName("hello,");
        });

        assertDoesNotThrow(() -> {
            client.setName("hello    ");
        });

        assert client.getName().equals("hello");
    }

    @Test
    void setPassword() {
        Client client = generateDefaultClient();

        assertThrows(IllegalArgumentException.class, () -> {
            client.setPassword("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            client.setPassword(",");
        });

        assertDoesNotThrow(() -> {
            client.setPassword("1234567890  ");
        });

        assert client.getPassword().equals("1234567890");
    }

    private Client generateDefaultClient() {
        return new Client("000000000", "Guess User", Gender.MALE, "123456");
    }
    
    @Test
    void clientDosntRepatInSet() {
        Client client = generateDefaultClient();
        Client client2 = generateDefaultClient();

        Set<Client> clients = new HashSet<>();

        clients.add(client);
        clients.add(client2);

        assert clients.size() == 1;

        assert  client.equals(client2);
    }
}