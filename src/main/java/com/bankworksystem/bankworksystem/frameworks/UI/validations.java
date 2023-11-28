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


    public static boolean validateAllFields(TextField clientID, TextField name,TextField password,
                                            CheckBox checkBox1, CheckBox checkBox2, ChoiceBox<String> choiceBox) {

        if (clientID == null || clientID.getText().trim().isEmpty() ||
                name == null || name.getText().trim().isEmpty() ||
                password == null || password.getText().trim().isEmpty()) {
            return false;
        }

        if ((checkBox1 == null || !checkBox1.isSelected()) && (checkBox2 == null || !checkBox2.isSelected())) {
            messageWindow.showErrorMessage("Error", "At least one CheckBox must be selected.");
            return false;
        }

        if (choiceBox == null || choiceBox.getValue() == null || choiceBox.getValue().isEmpty()) {
            messageWindow.showErrorMessage("Error", "ChoiceBox must have a selected value.");
            return false;
        }

        return true;
    }
}
