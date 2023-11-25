package com.bankworksystem.bankworksystem.frameworks.persistency;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TextClientPersistencyTest {

    private TextClientPersistency clientPersistency;

    public void createFileIfNotExists(Path pathToFile) throws IOException {
        // Check if the path does not exist or if it's not a regular file
        if (Files.notExists(pathToFile) || !Files.isRegularFile(pathToFile)) {
            // Attempt to create a new file
            Files.createFile(pathToFile);
        }
    }

    @BeforeEach
    public void setUp() throws IOException {
        clientPersistency = new TextClientPersistency("src/test/resources/", new ClientSerializer());
    }


    @AfterEach
    public void tearDown() throws IOException {

    }


    @Test
    public void testSaveAndGetClient() {
        Client client = new Client("123", "John Doe", Gender.FEMALE, "password1");
        clientPersistency.saveClient(client);

        Client retrievedClient = clientPersistency.getClient("123");
        assertEquals(client, retrievedClient);
    }

    @Test
    public void testDeleteClient() {
        Client client = new Client("123", "John Doe", Gender.MALE, "password1");
        clientPersistency.saveClient(client);
        clientPersistency.deleteClient("123");

        assertNull(clientPersistency.getClient("123"));
    }

    @Test
    public void testUpdateClient() {
        Client originalClient = new Client("123", "John Doe", Gender.MALE, "password1");
        clientPersistency.saveClient(originalClient);

        Client updatedClient = new Client("123", "Jane Doe", Gender.FEMALE, "password2");
        clientPersistency.updateClient("123", updatedClient);

        Client retrievedClient = clientPersistency.getClient("123");
        assertEquals(updatedClient, retrievedClient);
    }

    @Test
    public void testGetClients() {
        Client client1 = new Client("123", "John Doe", Gender.MALE, "password1");
        Client client2 = new Client("456", "Jane Doe", Gender.FEMALE, "password2");
        clientPersistency.saveClient(client1);
        clientPersistency.saveClient(client2);

        Set<Client> clients = clientPersistency.getClients();
        assertTrue(clients.contains(client1));
        assertTrue(clients.contains(client2));
    }
}
