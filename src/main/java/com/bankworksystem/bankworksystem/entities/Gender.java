package com.bankworksystem.bankworksystem.entities;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private String genderName;

    Gender(String genderName) {
        this.genderName = genderName;
    }

    public String getGenderName() {
        return genderName;
    }

    public static Gender getGenderByName(String genderName) {
        genderName = genderName.toUpperCase();

        return Gender.valueOf(genderName);
    }
}
