package com.bankworksystem.bankworksystem.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenderTest {

    @Test
    void getGenderByName() {
        assert Gender.getGenderByName("female") == Gender.FEMALE;
        assert Gender.getGenderByName("MaLe") == Gender.MALE;
        assert Gender.getGenderByName("OtHer") == Gender.OTHER;

        assertThrows(IllegalArgumentException.class, () -> {
            Gender.getGenderByName("femal");
        });
    }
}