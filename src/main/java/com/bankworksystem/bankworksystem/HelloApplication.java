package com.bankworksystem.bankworksystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("initWindow.fxml"));
        Parent root = fxmlLoader.load();

        URL imageUrl = HelloApplication.class.getResource("/images/logo.png");

        Image icon = new Image(imageUrl.toExternalForm());
        stage.getIcons().add(icon);
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);

        stage.setResizable(false);
        stage.setTitle("Bank system");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}