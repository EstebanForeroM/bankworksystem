package com.bankworksystem.bankworksystem.frameworks.persistency.UI;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class initWindowController {

    @FXML
    private ImageView exit;

    @FXML
    private Button managementClient;

    @FXML
    private Button managementProducts;

    @FXML
    private ImageView menu;

    @FXML
    private ImageView menu1;

    @FXML
    private AnchorPane menuPanel;

    @FXML
    private AnchorPane principalPanel;

    @FXML
    private Button transferents;

    private boolean isMenuPanelVisible = false;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void initialize(MouseEvent mouseEvent) {
        exit.setOnMouseClicked(event -> System.exit(0));
        System.out.println("init");
    }
    @FXML
    private void eventMenuPanel() {
        if (!isMenuPanelVisible) {
            menuPanel.setOpacity(0.0);

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), menuPanel);
            translateTransition.setByX(+250);
            translateTransition.play();

            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1), menuPanel);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();

            menuPanel.setVisible(true);
            isMenuPanelVisible = true;
        } else {
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1), menuPanel);
            translateTransition1.setByX(-250);
            translateTransition1.play();

            FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1), menuPanel);
            fadeOutTransition.setToValue(0.0);
            fadeOutTransition.play();

            fadeOutTransition.setOnFinished(e -> {
                menuPanel.setVisible(false);
                isMenuPanelVisible = false;
            });
        }
    }

    @FXML
    private void menuPanel(MouseEvent event) {
        eventMenuPanel();
    }
}