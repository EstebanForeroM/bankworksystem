package com.bankworksystem.bankworksystem.entities.products;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class CDTTest {

    private CDT cdt;
    private final String cdtId = "123456789";
    private final String ownerId = "987654321";
    private final Date openingDate = new Date();
    private final int expirationMonths = 12; // Example value

    @BeforeEach
    void setUp() {
        cdt = new CDT(cdtId, ownerId, openingDate, expirationMonths);
    }

    @Test
    void cdtInitializationTest() {
        assertEquals(cdtId, cdt.getId());
        assertEquals(ownerId, cdt.getOwnerId());
        assertEquals(openingDate, cdt.getOpeningDate());
        assertEquals(expirationMonths, cdt.getExpirationMonths());
    }

    @Test
    void setValidExpirationMonthsTest() {
        int newExpirationMonths = 24;
        cdt.setExpirationMonths(newExpirationMonths);
        assertEquals(newExpirationMonths, cdt.getExpirationMonths());
    }

    @Test
    void setNegativeExpirationMonthsTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cdt.setExpirationMonths(-6);
        });
        assertEquals("Invalid amount", exception.getMessage());
    }

    @Test
    void negativeExpirationMonthsInConstructorTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new CDT(cdtId, ownerId, openingDate, -12);
        });
        assertEquals("Invalid amount", exception.getMessage());
    }
}