package com.bankworksystem.bankworksystem.frameworks.UI;

import com.bankworksystem.bankworksystem.frameworks.Services;
import com.bankworksystem.bankworksystem.useCases.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class passwordWindowController {

    @FXML
    private Button enter;

    @FXML
    private ImageView exit;

    @FXML
    private TextField password;

    @FXML
    private ImageView pricipalWindow;

    @FXML
    private ImageView returnWindow;

    private static Token userToken;

    @FXML
    private void initialize() {
        // TODO
    }

    @FXML
    private void buttonPassword(ActionEvent event) throws Exception {
        String enteredPassword = password.getText();

        try {
            userToken = Services.getTokenAuthenticationService().getToken(enteredPassword);
            String fxml = "transferentsWindow.fxml";
            Node sourceNode = (Node) event.getSource();
            Navigation navigation = Navigation.getInstance();
            navigation.navigateToRemplaceScene("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
        } catch (Exception e) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "YOU PASSWORD IS WRONG");
        }
    }

    @FXML
    private void buttonImgPrincipalWindow(MouseEvent event) {
        pricipalWindow.setOnMouseClicked(e -> {
            String fxml = "initWindow.fxml";
            Node sourceNode = (Node) event.getSource();
            Navigation navigation = Navigation.getInstance();
            navigation.navigateToRemplaceScene("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
        });
    }

    @FXML
    private void buttonImgReturnWindow(MouseEvent event) {
        returnWindow.setOnMouseClicked(e -> {
            String fxml = "initWindow.fxml";
            Node sourceNode = (Node) event.getSource();
            Navigation navigation = Navigation.getInstance();
            navigation.navigateToRemplaceScene("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
        });
    }

    public static Token getUserToken(){
        return userToken;
    }
}
