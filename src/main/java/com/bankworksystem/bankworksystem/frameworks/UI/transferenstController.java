package com.bankworksystem.bankworksystem.frameworks.UI;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class transferenstController {

    @FXML
    private Button advance;

    @FXML
    private Button balance;

    @FXML
    private Button buy;

    @FXML
    private Button deposit;

    @FXML
    private Button payments;

    @FXML
    private ImageView pricipalWindow;

    @FXML
    private ImageView returnWindow;

    @FXML
    private Button shangePassword;

    @FXML
    private ChoiceBox<?> typeOfProducts;

    @FXML
    private Button withdrawals;

    @FXML
    private void initialize() {
        // TODO
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

}