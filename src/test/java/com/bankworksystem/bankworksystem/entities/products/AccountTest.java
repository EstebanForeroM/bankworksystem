package com.bankworksystem.bankworksystem.entities.products;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class AccountTest {

    private Account account;
    private final String accountId = "123";
    private final String ownerId = "346341";
    private final Date openingDate = new Date();
    private final AccountType type = AccountType.SAVINGS; // Assuming AccountType is an enum

    @BeforeEach
    void setUp() {
        account = new Account(accountId, ownerId, openingDate, type);
    }

    @Test
    void accountInitializationTest() {
        assertEquals(accountId, account.getId());
        assertEquals(ownerId, account.getOwnerId());
        assertEquals(openingDate, account.getOpeningDate());
        assertEquals(type, account.getType());
    }

    @Test
    void depositValidAmountTest() {
        account.deposit(100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void depositNegativeAmountTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-50.0);
        });
        assertEquals("Invalid amount", exception.getMessage());
    }

    @Test
    void withdrawValidAmountTest() {
        account.deposit(200.0);
        account.withdraw(100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void withdrawInsufficientFundsTest() {
        account.deposit(50.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(100.0);
        });
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void withdrawNegativeAmountTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-50.0);
        });
        assertEquals("Invalid amount", exception.getMessage());
    }
}
