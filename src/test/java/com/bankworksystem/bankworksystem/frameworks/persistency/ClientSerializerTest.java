package com.bankworksystem.bankworksystem.frameworks.persistency;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientSerializerTest {
    private ClientSerializer serializer;
    private Client testClient;
    private String testClientString;

    @BeforeEach
    public void setUp() {
        serializer = new ClientSerializer();
        testClient = new Client("12345", "John Doe", Gender.MALE, "password123");
        testClientString = "John Doe,12345,Male,password123";
    }

    @Test
    public void testSerialize() {
        String serialized = serializer.serialize(testClient);
        assertEquals(testClientString, serialized);
    }

    @Test
    public void testDeserialize() {
        Client deserialized = serializer.deserialize(testClientString);
        assertEquals(testClient.getId(), deserialized.getId());
        assertEquals(testClient.getName(), deserialized.getName());
        assertEquals(testClient.getGender(), deserialized.getGender());
        assertEquals(testClient.getPassword(), deserialized.getPassword());
    }

    @Test
    public void testSerializeWithNullClient() {
        assertThrows(NullPointerException.class, () -> serializer.serialize(null));
    }

    @Test
    public void testDeserializeWithInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> serializer.deserialize("invalid,string,with,four,parts"));
    }
}