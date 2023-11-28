package com.bankworksystem.bankworksystem.frameworks.UI;

import javafx.scene.control.TextField;


public class validations {

    private static MessageWindow messageWindow = new MessageWindow();
    public static String validateClientID(String clientID) {
        if (clientID.length() != 10) {
            messageWindow.showErrorMessage("Error", "The length must be 10 numbers.");
            return clientID;
        }

        if (!clientID.matches("\\d+")) {
            messageWindow.showErrorMessage("Error", "The ID should only contain numbers!");
            return deleteNonAllowedCharacters(clientID, "[^\\d]");
        }

        return clientID;
    }

    public static String validateName(String name) {
        if (!name.matches("[a-zA-Z ]+")) {
            messageWindow.showErrorMessage("Error", "The Name should only contain letters and spaces!");
            return deleteNonAllowedCharacters(name, "[^a-zA-Z ]");
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

    private static String deleteNonAllowedCharacters(String text, String regex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (!Character.toString(text.charAt(i)).matches(regex))
                stringBuilder.append(text.charAt(i));
        }
        return stringBuilder.toString().trim();
    }

    public static  boolean validateTransactionAmount(double amount) {
        return amount > 0;
    }
}
