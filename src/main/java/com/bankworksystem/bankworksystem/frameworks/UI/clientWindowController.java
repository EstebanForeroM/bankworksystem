package com.bankworksystem.bankworksystem.frameworks.UI;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Gender;
import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.ProductType;
import com.bankworksystem.bankworksystem.entities.products.UninitializedProduct;
import com.bankworksystem.bankworksystem.frameworks.Services;
import com.bankworksystem.bankworksystem.useCases.Token;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.CheckBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
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

    private int index= 0;

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
        resetSelectedImage();

        updateClients();
        Services.addOnClientAddedListener(this::updateClients);
    }

    private void updateClients() {
        actualClients = Services.getClientSearcher().getClients();
    }

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

        saveProductChanges();

        MessageWindow messageWindow = new MessageWindow();
        messageWindow.showSuccessMessage("Success", "Client saved successfully");
    }

    private void saveProductChanges() {
        Set<Product> productSet = Services.getProductSearcher().getProductsByUniqueOwner(clientID.getText());
        Set<ProductType> productsAlreadyAdded = new HashSet<>();

        for (Product product : productSet) {
            ProductType productType = ProductType.getProductType(product);
            if (productType == ProductType.UninitializedProduct) {
                productType = ((UninitializedProduct) product).getProductType();
            }

            productsAlreadyAdded.add(productType);

            if (productType == ProductType.SAVINGS_ACCOUNT) {
                if (!sanvingsAccount.isSelected()) {
                    Services.getDeletionService().deleteProduct(product.getId());
                }
            } else if (productType == ProductType.CHECKING_ACCOUNT) {
                if (!currentAccount.isSelected()) {
                    Services.getDeletionService().deleteProduct(product.getId());
                }
            } else if (productType == ProductType.CDT) {
                if (!cdt.isSelected()) {
                    Services.getDeletionService().deleteProduct(product.getId());
                }
            } else if (productType == ProductType.VISA_CARD) {
                if (!visaCard.isSelected()) {
                    Services.getDeletionService().deleteProduct(product.getId());
                }
            } else if (productType == ProductType.AMERICAN_EXPRESS) {
                if (!americanCard.isSelected()) {
                    Services.getDeletionService().deleteProduct(product.getId());
                }
            }
        }

        if (sanvingsAccount.isSelected())
            if (!productsAlreadyAdded.contains(ProductType.SAVINGS_ACCOUNT))
                Services.getProductCreationService().addProduct(Services.getTokenAuthenticationService().getToken(password.getText()), ProductType.SAVINGS_ACCOUNT);
        if (currentAccount.isSelected())
            if (!productsAlreadyAdded.contains(ProductType.CHECKING_ACCOUNT))
                Services.getProductCreationService().addProduct(Services.getTokenAuthenticationService().getToken(password.getText()), ProductType.CHECKING_ACCOUNT);
        if (cdt.isSelected())
            if (!productsAlreadyAdded.contains(ProductType.CDT))
                Services.getProductCreationService().addProduct(Services.getTokenAuthenticationService().getToken(password.getText()), ProductType.CDT);
        if (visaCard.isSelected())
            if (!productsAlreadyAdded.contains(ProductType.VISA_CARD))
                Services.getProductCreationService().addProduct(Services.getTokenAuthenticationService().getToken(password.getText()), ProductType.VISA_CARD);
        if (americanCard.isSelected())
            if (!productsAlreadyAdded.contains(ProductType.AMERICAN_EXPRESS))
                Services.getProductCreationService().addProduct(Services.getTokenAuthenticationService().getToken(password.getText()), ProductType.AMERICAN_EXPRESS);
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
    private void buttonImgPrincipalWindow(MouseEvent event) {
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
        if (actualClients.isEmpty())
            return;
        if (index < actualClients.size() - 1)
            index++;
        updateWithClient();
    }

    @FXML
    private void buttonLeft(MouseEvent event) {
        if (actualClients.isEmpty())
            return;
        if (index > 0)
            index--;
        updateWithClient();
    }

    private void updateWithClient() {
        Client selectedClient = actualClients.get(index);
        nameUser.setText(selectedClient.getName());
        gender.setValue(selectedClient.getGender().getGenderName());
        clientID.setText(selectedClient.getId());
        Path pathToImage = Services.getImagePersistence().searchImageByClientId(selectedClient.getId());
        Image image = new Image(pathToImage.toUri().toString());
        clientImage.setImage(image);

        setAllChoiceBoxesFalse();

        Set<Product> productSet = Services.getProductSearcher().getProductsByUniqueOwner(selectedClient.getId());

        for (Product product : productSet) {
            ProductType productType = ProductType.getProductType(product);
            if (productType == ProductType.UninitializedProduct) {
                productType = ((UninitializedProduct) product).getProductType();
            }

            if (productType == ProductType.SAVINGS_ACCOUNT) {
                sanvingsAccount.setSelected(true);
            } else if (productType == ProductType.CHECKING_ACCOUNT) {
                currentAccount.setSelected(true);
            } else if (productType == ProductType.CDT) {
                cdt.setSelected(true);
            } else if (productType == ProductType.VISA_CARD) {
                visaCard.setSelected(true);
            } else if (productType == ProductType.AMERICAN_EXPRESS) {
                americanCard.setSelected(true);
            }
        }
    }

    private void setAllChoiceBoxesFalse() {
        sanvingsAccount.setSelected(false);
        currentAccount.setSelected(false);
        cdt.setSelected(false);
        visaCard.setSelected(false);
        americanCard.setSelected(false);
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

        resetSelectedImage();
    }

    @FXML
    private void buttonDeleteUser(ActionEvent event) {
        try {
            if (Services.getClientSearcher().userExists(clientID.getText())) {
                Services.getDeletionService().deleteClient(clientID.getText());
                MessageWindow messageWindow = new MessageWindow();
                messageWindow.showSuccessMessage("Success", "Client deleted successfully");
            }
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



        try {
            Services.getUserCreationService().createClient(clientName, clientPassword, clientGender, clientId);
            Token clientToken = Services.getTokenAuthenticationService().getToken(clientPassword);
            addSelectedProducts(clientToken);
            saveImage(clientId);

            resetSelectedImage();
        }
        catch (Exception e) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", e.getMessage());
        }
    }

    private void saveImage(String clientId) {
        try {
            URI uri = new URI(imagePath);
            if (!uri.isAbsolute()) {
                throw new IllegalArgumentException("URI is not absolute: " + imagePath);
            }
            Path path = Paths.get(uri);
            Services.getImagePersistence().save(path, clientId);
        } catch (URISyntaxException e) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "Invalid image path or URL");
        } catch (IllegalArgumentException e) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", e.getMessage());
        } catch (IOException e) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "Unable to save client image");
        }
    }

    private void resetSelectedImage() {
        URL imageUrl = getClass().getResource("/Images/defaultImage.png");
        if (imageUrl != null) {
            imagePath = imageUrl.toString();
            Image image = new Image(imagePath);
            clientImage.setImage(image);
        } else {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "Default image not found");
        }
    }

}
