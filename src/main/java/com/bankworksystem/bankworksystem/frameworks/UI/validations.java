package com.bankworksystem.bankworksystem.frameworks.UI;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


public class validations {

    private static MessageWindow messageWindow = new MessageWindow();
    public static String validateClientID(String clientID) {
        if (clientID.length() > 10) {
            messageWindow.showErrorMessage("Error", "The length must be 10 numbers.");
            return clientID.substring(0, 10);
        }

        if (!clientID.matches("\\d+")) {
            messageWindow.showErrorMessage("Error", "The ID should only contain numbers!");
            return null;
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

    public static boolean validateEnterPassword(String password) {
        return password != null && !password.isEmpty();
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


    public static boolean validateAllFields(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                messageWindow.showErrorMessage("Error", "All fields are required.");
                return false;
            }
        }
        return true;
    }

    public static boolean validateAllChoiceBoxes(ChoiceBox... fields) {
        for (ChoiceBox field : fields) {
            if (field.getValue() == null || field.getValue().toString().isEmpty()) {
                messageWindow.showErrorMessage("Error", "All fields are required.");
                return false;
            }
        }
        return true;
    }
}
