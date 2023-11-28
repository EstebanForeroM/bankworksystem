package com.bankworksystem.bankworksystem.frameworks.UI;

import javafx.application.Application;
import javafx.stage.Stage;

public class validations {
    public static boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage ("Error", "The length must be 10 numbers.");
            return false;
        }

        if (phoneNumber.length() == 0) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage ("Error", "You must enter a ID");
            return false;
        }

        if (!phoneNumber.matches("\\d+")) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "The ID should only contain numbers!");
            return false;
        }

        return true;
    }

    public static boolean validateName(String name) {
        if (!name.matches("[a-zA-Z]+")) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "The Name should only contain letters!");
            return false;
        }

        if (name.trim().isEmpty()) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "You must enter a value for Name.Machines");
            return false;
        }

        return true;
    }

    public static  boolean validatePassword(String password) {
        if (password.length() > 6) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage ("Error", "The length must be minimun 6 numbers.");
            return false;
        }

        if (password.length() == 0) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage ("Error", "You must enter a Password");
            return false;
        }

        return true;
    }
}
