package com.bankworksystem.bankworksystem.entities;

import com.bankworksystem.bankworksystem.frameworks.UI.MessageWindow;

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
        if (genderName == null) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error!","The gender null");
            return null;
        }
        genderName = genderName.toUpperCase();
        return Gender.valueOf(genderName);
    }
}
