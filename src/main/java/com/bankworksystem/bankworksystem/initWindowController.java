package com.bankworksystem.bankworksystem;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class initWindowController {
    public ImageView menu1;
    @FXML
    private Button managementClients;

    @FXML
    private Button managementProducts;

    @FXML
    private Button transferents;

    @FXML
    private ImageView bankLogo;

    @FXML
    private ImageView menu,exit;

    @FXML
    private Text bankName;

    @FXML
    private AnchorPane principalPanel;

    @FXML
    private AnchorPane panelNavigation;

    @FXML
    private void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(event -> System.exit(0));
        panelNavigation.setVisible(false);

        menu.setOnMouseClicked(event -> {
            // Muestra u oculta el panel de navegación al hacer clic en el menú
            panelNavigation.setVisible(!panelNavigation.isVisible());

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(1), principalPanel);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                principalPanel.setVisible(!panelNavigation.isVisible());

                FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(1), principalPanel);
                fadeTransition2.setFromValue(0);
                fadeTransition2.setToValue(0.15);
                fadeTransition2.play();

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), principalPanel);
                translateTransition1.setByY(+600);
                translateTransition1.play();
            });
        });
    }
}