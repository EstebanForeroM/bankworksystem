package com.bankworksystem.bankworksystem.frameworks.UI;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Box;

public class productWindowController {

    @FXML
    private Box americanCard;

    @FXML
    private Box cdt;

    @FXML
    private Button clean;

    @FXML
    private TextField clientID;

    @FXML
    private Box currentAccount;

    @FXML
    private Button deleteUser;

    @FXML
    private Button editProfile;

    @FXML
    private ChoiceBox<?> gender;

    @FXML
    private ImageView left;

    @FXML
    private TextField nameUser;

    @FXML
    private TextField password;

    @FXML
    private ImageView pricipalWindow;

    @FXML
    private ImageView returnWindow;

    @FXML
    private ImageView right;

    @FXML
    private Box sanvingsAccount;

    @FXML
    private Button saveChanges;

    @FXML
    private Button searchImg;

    @FXML
    private Button seeAll;

    @FXML
    private Box visaCard;

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

