package com.bankworksystem.bankworksystem.frameworks.UI;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Gender;
import com.bankworksystem.bankworksystem.entities.products.ProductType;
import com.bankworksystem.bankworksystem.frameworks.Services;
import com.bankworksystem.bankworksystem.useCases.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Box;
import javafx.scene.control.CheckBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class clientWindowController {

    @FXML
    private CheckBox americanCard;

    @FXML
    private CheckBox cdt;

    @FXML
    private Button clean;

    @FXML
    private TextField clientID;

    @FXML
    private CheckBox currentAccount;

    @FXML
    private Button deleteUser;

    @FXML
    private Button editProfile;

    @FXML
    private ChoiceBox<String> gender;

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
    private ImageView clientImage;

    @FXML
    private ImageView right;

    @FXML
    private CheckBox sanvingsAccount;

    @FXML
    private Button saveChanges;

    @FXML
    private Button searchImg;

    @FXML
    private Button seeAll;

    @FXML
    private CheckBox visaCard;

    private String imagePath;

    private List<Client> actualClients;

    private int actualClientIndex = 0;

    private void clientListChanges() {
        actualClients = Services.getClientSearcher().getClients();
    }

    private void saveClient() {
        String clientName = nameUser.getText();
        String clientId= clientID.getText();
        Gender clientGender = Gender.getGenderByName(gender.getValue());
        String clientPassword = password.getText();

        if (!Services.getClientSearcher().userExists(clientId)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\"The client don't exists, you must create one client first\"");
            alert.showAndWait();
            return;
        }

        try {

            Token clientToken = Services.getTokenAuthenticationService().getToken(clientPassword);

            Services.getUserModificationService().modifyUserName(clientToken, clientName);
            Services.getUserModificationService().modifyUserGender(clientToken, clientGender);
            Services.getUserModificationService().modifyUserPhoto(clientToken, imagePath);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\"Error!\"");
            alert.showAndWait();
            return;
        }
    }

    private void addSelectedProducts(Token token) {
        if (sanvingsAccount.isSelected()) {
            Services.getProductCreationService().addProduct(token, ProductType.SAVINGS_ACCOUNT);
        }
        if (currentAccount.isSelected()) {
            Services.getProductCreationService().addProduct(token, ProductType.CHECKING_ACCOUNT);
        }
        if (cdt.isSelected()) {
            Services.getProductCreationService().addProduct(token, ProductType.CDT);
        }
        if (visaCard.isSelected()) {
            Services.getProductCreationService().addProduct(token, ProductType.VISA_CARD);
        }
        if (americanCard.isSelected()) {
            Services.getProductCreationService().addProduct(token, ProductType.AMERICAN_EXPRESS);
        }
    }

    @FXML
    private void initialize() {
        actualClients = new ArrayList<>();
        Services.addOnClientAddedListener(this::clientListChanges);
        imagePath = "@../../../../../img/defaultProfile.png";
        Gender[] genders = Gender.values();
        String[] genderNames = new String[genders.length];
        for (int i = 0; i < genderNames.length; i++) {
            genderNames[i] = genders[i].getGenderName();
        }
        gender.getItems().addAll(genderNames);
        clientListChanges();
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

    @FXML
    private void buttonEditProfile(ActionEvent event) {

    }

    @FXML
    private void buttonSearchImg(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            clientImage.setImage(image);
        } else {
            System.out.println("No file selected.");
        }
    }

    @FXML
    private void buttonClean(ActionEvent event) {
        this.sanvingsAccount.setSelected(false);
        this.currentAccount.setSelected(false);
        this.cdt.setSelected(false);
        this.visaCard.setSelected(false);
        this.americanCard.setSelected(false);

        clientID.setText("");
        nameUser.setText("");
        password.setText("");
    }

    @FXML
    private void buttonDeleteUser(ActionEvent event) {
        try {
            String clientName = nameUser.getText();
            String clientId= clientID.getText();
            Gender clientGender = Gender.getGenderByName(gender.getValue());
            String clientPassword = password.getText();
            Token token = Services.getTokenAuthenticationService().getToken(clientPassword);
            Services.getUserModificationService().modifyUser(token, clientName, clientPassword, clientGender, imagePath);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\"Error!\"");
            alert.showAndWait();
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void buttonSeeAll(ActionEvent event) {
        String fxml = "seeAllClients.fxml";
        Node sourceNode = (Node) event.getSource();
        Navigation navigation = Navigation.getInstance();
        navigation.navigateToRemplaceScene("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
    }

    @FXML
    private void buttonSaveChanges(ActionEvent event) {
        String clientName = nameUser.getText();
        String clientId= clientID.getText();
        Gender clientGender = Gender.getGenderByName(gender.getValue());
        String clientPassword = password.getText();

        if (!Services.getClientSearcher().userExists(clientId)) {

            Services.getUserCreationService().createClient(clientName, clientPassword, clientGender, clientId, imagePath);
            Token clientToken =  Services.getTokenAuthenticationService().getToken(clientPassword);
            addSelectedProducts(clientToken);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\"Error!This client already exists\"");
            alert.showAndWait();
            return;
        }

        try {
            /*Services.getUserCreationService().createClient(clientName, clientPassword, clientGender, clientId, imagePath);
            Token clientToken =  Services.getTokenAuthenticationService().getToken(clientPassword);
            addSelectedProducts(clientToken);*/
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\"Error!Client created successfully\"");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("\"Error!Client created successfully\"");
            alert.showAndWait();
        }
        saveClient();
    }
}
