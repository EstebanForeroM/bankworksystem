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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Box;
import javafx.scene.control.CheckBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.bankworksystem.bankworksystem.frameworks.UI.validations.*;

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

    private void clientListChanges() {
        actualClients = Services.getClientSearcher().getClients();
    }

    private void saveClient() {
        String clientName = nameUser.getText();
        String clientId= clientID.getText();
        Gender clientGender = Gender.getGenderByName(gender.getValue());
        String clientPassword = password.getText();

        saveImage(clientId);

        try {
            Token clientToken = Services.getTokenAuthenticationService().getToken(clientPassword);

            Services.getUserModificationService().modifyUserName(clientToken, clientName);
            Services.getUserModificationService().modifyUserGender(clientToken, clientGender);

        } catch (Exception e) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", e.getMessage());
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
        System.out.println("buttonImgPrincipalWindow");
            String fxml = "initWindow.fxml";
            Node sourceNode = (Node) event.getSource();
            Navigation navigation = Navigation.getInstance();
            navigation.navigateToRemplaceScene("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
    }

    @FXML
    private void buttonImgReturnWindow(MouseEvent event) {
            String fxml = "initWindow.fxml";
            Node sourceNode = (Node) event.getSource();
            Navigation navigation = Navigation.getInstance();
            navigation.navigateToRemplaceScene("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
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
    private void buttonRight(MouseEvent event) {
    }

    @FXML
    private void buttonLeft(MouseEvent event) {
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
            if (Services.getClientSearcher().userExists(clientID.getText()))
                Services.getDeletionService().deleteClient(clientID.getText());
            else {
                MessageWindow messageWindow = new MessageWindow();
                messageWindow.showErrorMessage("Error", "Error! This client does not exist");
            }
        } catch (Exception e) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "Error!");
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void buttonSeeAll(ActionEvent event) {
        Set<Client> allClients = Services.getClientSearcher().getAllClients();

        if (allClients.isEmpty()) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "No clients found.");
            return;
        }

        String fxml = "seeAllClients.fxml";
        Node sourceNode = (Node) event.getSource();
        Navigation navigation = Navigation.getInstance();
        navigation.navigationWithException("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
    }

    @FXML
    private void validationName(KeyEvent event) {
        String userName = nameUser.getText();
        nameUser.setText(validateName(userName));
        nameUser.end();
    }

    @FXML
    private void validationId(KeyEvent event) {
        String ClientID = clientID.getText();
        clientID.setText(validateClientID(ClientID));
        clientID.end();
    }

    @FXML
    private void buttonSaveChanges(ActionEvent event) {
        if (!validateAllFields(nameUser, clientID, password) || !validateAllChoiceBoxes(gender)) {
            return;
        }
        String clientId = clientID.getText();

        if (!Services.getClientSearcher().userExists(clientId)) {
            createClient(clientId);
        } else {
            saveClient();
        }
    }

    private void createClient(String clientId) {
        String clientName = nameUser.getText();
        String clientPassword = password.getText();
        Gender clientGender = Gender.getGenderByName(gender.getValue());



        Services.getUserCreationService().createClient(clientName, clientPassword, clientGender, clientId);
        Token clientToken = Services.getTokenAuthenticationService().getToken(clientPassword);
        addSelectedProducts(clientToken);
        saveImage(clientId);

        MessageWindow messageWindow = new MessageWindow();
        messageWindow.showErrorMessage("Error", "Error! This client already exists");

        resetSelectedImage();
    }

    private void saveImage(String clientId) {
        try {
            Services.getImagePersistence().save(Path.of(imagePath), clientId);
        } catch (IOException e) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "Unable to save client image");
        }
    }

    private void resetSelectedImage() {
        Image image = new Image("@../../../../../img/defaultProfile.png");
        imagePath = "@../../../../../img/defaultProfile.png";
        clientImage.setImage(image);
    }
}
