package com.bankworksystem.bankworksystem.entities.products;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class CardTest {

    private Card card;
    private final String cardId = "123456"; // Numeric ID for the card
    private final String ownerId = "987654"; // Numeric ID for the owner
    private final Date openingDate = new Date();
    private final CardType type = CardType.AMERICAN_EXPRESS; // Assuming CardType is an enum

    @BeforeEach
    void setUp() {
        card = new Card(cardId, ownerId, openingDate, type);
    }

    @Test
    void cardInitializationTest() {
        assertEquals(cardId, card.getId());
        assertEquals(ownerId, card.getOwnerId());
        assertEquals(openingDate, card.getOpeningDate());
        assertEquals(type, card.getType());
    }

    @Test
    void depositValidAmountTest() {
        card.deposit(100.0);
        assertEquals(100.0, card.getBalance());
    }

    @Test
    void depositNegativeAmountTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            card.deposit(-50.0);
        });
        assertEquals("Invalid amount", exception.getMessage());
    }

    @Test
    void withdrawValidAmountTest() {
        card.deposit(200.0);
        card.withdraw(100.0);
        assertEquals(100.0, card.getBalance());
    }

    @Test
    void withdrawInsufficientFundsTest() {
        card.deposit(50.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            card.withdraw(100.0);
        });
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void withdrawNegativeAmountTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            card.withdraw(-50.0);
        });
        assertEquals("Invalid amount", exception.getMessage());
    }
}