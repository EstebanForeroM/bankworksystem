package com.bankworksystem.bankworksystem.frameworks.UI;

import com.bankworksystem.bankworksystem.HelloApplication;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

public class initWindowController {

    @FXML
    private ImageView exit;

    @FXML
    private Button clientsManagement;

    @FXML
    private Button managementProducts;

    @FXML
    private ImageView menu;

    @FXML
    private ImageView menu1;

    @FXML
    private AnchorPane menuPanel;

    @FXML
    private AnchorPane menuPanel1;

    @FXML
    private AnchorPane principalPanel;

    @FXML
    private Button transferents;

    private boolean isMenuPanelVisible = false;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void exit(MouseEvent mouseEvent) {
        exit.setOnMouseClicked(event -> System.exit(0));
        System.out.println("init");
    }
    @FXML
    private void eventMenuPanel() {
        if (!isMenuPanelVisible) {

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), menuPanel);
            translateTransition.setByX(+250);
            translateTransition.play();

            menuPanel.setVisible(true);
            isMenuPanelVisible = true;
        } else {

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), menuPanel);
            translateTransition1.setByX(-250);
            translateTransition1.play();

            translateTransition1.setOnFinished(e -> {
                menuPanel.setVisible(false);
                isMenuPanelVisible = false;
            });
        }
    }

    @FXML
    private void menuPanel(MouseEvent event) {
        eventMenuPanel();
    }

    @FXML
    private void buttonmanagementClient(ActionEvent event) throws IOException {
        String fxml = "clientWindow.fxml";
        Node sourceNode = (Node) event.getSource();
        Navigation navigation = Navigation.getInstance();
        navigation.navigateToRemplaceScene("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
    }


    @FXML
    private void buttonmanagementProducts(ActionEvent event) throws IOException {
        String fxml = "productWindow.fxml";
        Node sourceNode = (Node) event.getSource();
        Navigation navigation = Navigation.getInstance();
        navigation.navigationWithException("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
    }

    @FXML
    private void buttontransferenst(ActionEvent event) throws IOException {
        String fxml = "passwordWindow.fxml";
        Node sourceNode = (Node) event.getSource();
        Navigation navigation = Navigation.getInstance();
        navigation.navigationWithException("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
    }

    @FXML
    private void buttonExit(MouseEvent event) {
        System.exit(0);
    }
}