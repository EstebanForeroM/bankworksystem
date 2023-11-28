package com.bankworksystem.bankworksystem.frameworks.UI;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.frameworks.Services;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class Navigation {
    private static Navigation instance = new Navigation();

    public static Navigation getInstance() {
        return instance;
    }

    private Navigation() {
    }

    public void fireEvent(Stage currentStage, EventType<Event> root, SceneChangeEvent sceneChangeEvent) {
        Event.fireEvent(currentStage, sceneChangeEvent);
    }

    public static class SceneChangeEvent extends Event {
        public static final EventType<SceneChangeEvent> SCENE_CHANGE_EVENT = new EventType<>("SCENE_CHANGE");

        private Scene newScene;

        public SceneChangeEvent(Scene newScene) {
            super(SCENE_CHANGE_EVENT);
            this.newScene = newScene;
        }

        public Scene getNewScene() {
            return newScene;
        }
    }

    public void fireEventSceneChange(Node source, Scene newScene) {
        SceneChangeEvent sceneChangeEvent = new SceneChangeEvent(newScene);
        Event.fireEvent(source, sceneChangeEvent);
    }

    public void navigateToRemplaceScene(String fxmlPath, Node sourceNode) {
        try {
            Set<Client> allClients = Services.getClientSearcher().getAllClients();
            if (!allClients.isEmpty()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                if (root != null) {
                    Scene newScene = new Scene(root);

                    Stage currentStage = (Stage) sourceNode.getScene().getWindow();
                    currentStage.setScene(newScene);
                    currentStage.show();

                    fireEventSceneChange(sourceNode, newScene);
                } else {
                    System.err.println("Error: FXML root is null");
                }
            } else {
                MessageWindow messageWindow = new MessageWindow();
                messageWindow.showErrorMessage("Error", "There are no clients! Create one.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}