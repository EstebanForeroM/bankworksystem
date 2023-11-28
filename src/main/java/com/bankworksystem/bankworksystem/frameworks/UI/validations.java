package com.bankworksystem.bankworksystem.frameworks.UI;

import javafx.scene.control.TextField;


public class validations {

    private static MessageWindow messageWindow = new MessageWindow();
    public static boolean validateClientID(String clientID) {
        if (clientID.length() != 10) {
            messageWindow.showErrorMessage("Error", "The length must be 10 numbers.");
            return false;
        }

        if (clientID.isEmpty()) {
            messageWindow.showErrorMessage("Error", "You must enter an ID");
            return false;
        }

        if (!clientID.matches("\\d+")) {
            messageWindow.showErrorMessage("Error", "The ID should only contain numbers!");
            return false;
        }

        return true;
    }

    public static String validateName(String name) {
        if (!name.matches("[a-zA-Z]+")) {
            messageWindow.showErrorMessage("Error", "The Name should only contain letters!");
            return deleteLastCharacter(name);
        }
        return name;
    }

    public static boolean validatePassword(String password) {
        if (password.length() < 6) {
            messageWindow.showErrorMessage("Error", "The length must be at least 6 characters.");
            return false;
        }

        return true;
    }

    public static boolean validationAreAllTextFieldsFilled(TextField clienID, TextField emailField, TextField phoneField) {
        if (clienID == null || clienID.getText().trim().isEmpty()) {
            return false;
        }
        if (emailField == null || emailField.getText().trim().isEmpty()) {
            return false;
        }
        if (phoneField == null || phoneField.getText().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private static String deleteLastCharacter(String text) {
        if (text.length() > 0)
            text = text.substring(0, text.length() - 1);
        return text;
    }
}
