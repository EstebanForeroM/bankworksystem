package com.bankworksystem.bankworksystem.frameworks.persistency;

import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.UnexpectedException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProductSerializerTest {

    private ProductSerializer serializer;
    private Product card, account, cdt, uninitializedProduct;

    @BeforeEach
    void setUp() {
        serializer = new ProductSerializer();
        card = new Card("1", "3451", new Date(), CardType.VISA);
        account = new Account("2", "3452", new Date(), AccountType.CHECKING);
        cdt = new CDT("3", "6663", new Date(), 12);
        uninitializedProduct = new UninitializedProduct("4", "6664", ProductType.CDT);
    }

    @Test
    void serializeAndDeserializeCard() {
        String serialized = serializer.serialize(card);
        Product deserialized = serializer.deserialize(serialized);
        assertTrue(deserialized instanceof Card);
        assertEquals(card, deserialized);
    }

    @Test
    void serializeAndDeserializeGeneralProduct() {
        String serialized = serializer.serialize(card);
        Product deserialized = serializer.deserialize(serialized);
        assertTrue(deserialized instanceof Card);
        assertEquals(card, deserialized);
        assertEquals(card.getProductName(), deserialized.getProductName());
        assertEquals(card.getOwnerId(), deserialized.getOwnerId());
        assertEquals(card.getId(), deserialized.getId());
        assertEquals(card.getBalance(), deserialized.getBalance());
    }

    @Test
    void serializeAndDeserializeUninitializedProduct() {
        String serialized = serializer.serialize(uninitializedProduct);
        Product deserialized = serializer.deserialize(serialized);
        assertTrue(deserialized instanceof UninitializedProduct);
        assertEquals(uninitializedProduct, deserialized);
    }

    @Test
    void serializeAndDeserializeAccount() {
        String serialized = serializer.serialize(account);
        Product deserialized = serializer.deserialize(serialized);
        assertTrue(deserialized instanceof Account);
        assertEquals(account, deserialized);
    }

    @Test
    void serializeAndDeserializeCDT() {
        String serialized = serializer.serialize(cdt);
        Product deserialized = serializer.deserialize(serialized);
        assertTrue(deserialized instanceof CDT);
        assertEquals(cdt, deserialized);
    }

    @Test
    void invalidSerializationInput() {
        assertThrows(NullPointerException.class, () -> serializer.serialize(null));
    }

    @Test
    void invalidDeserializationInput() {
        assertThrows(IllegalArgumentException.class, () -> serializer.deserialize("invalid,string"));
    }
}