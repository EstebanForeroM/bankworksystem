package com.bankworksystem.bankworksystem.frameworks.UI;
import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.ProductType;
import com.bankworksystem.bankworksystem.entities.products.UninitializedProduct;
import com.bankworksystem.bankworksystem.frameworks.Services;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Box;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class productWindowController {

    public TextField clientId;
    @FXML
    private ImageView pricipalWindow;

    @FXML
    private ImageView returnWindow;

    @FXML
    private Button seeAll;
    @FXML
    private Button seachByID;
    @FXML
    private ChoiceBox searchProductOfClient;
    @FXML
    private DatePicker date;
    @FXML
    private TextField numProduct;
    @FXML
    private TextField balance;
    @FXML
    private TextField termInMonths;
    @FXML
    private Button create;
    @FXML
    private Button modify;
    @FXML
    private Button eliminate;

    private boolean productLoaded = false;
    private String actualClientId;
    private MessageWindow messageWindow;

    @FXML
    private void initialize() {
        messageWindow = new MessageWindow();

        create.setOnAction(e -> onCreateButtonClicked());
        clientId.setOnAction(e -> onClientIdEntered());
        seachByID.setOnAction(e -> onClientIdEntered());
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

    private void onCreateButtonClicked() {
        if (!productLoaded) {
            messageWindow.showErrorMessage("Error", "Client products not loaded");
            return;
        }


    }

    private void loadClientProducts(String clientId) {

        Set<Product> productList = Services.getProductSearcher().getProductsByUniqueOwner(clientId);

        String[] productNames = productList.stream()
                .map(product -> product instanceof UninitializedProduct
                        ? ((UninitializedProduct) product).getProductType().getName()
                        : product.getProductName())
                .toArray(String[]::new);

        searchProductOfClient.getItems().clear();
        searchProductOfClient.getItems().addAll(productNames);
    }


    private void onClientIdEntered() {
        actualClientId = clientId.getText();
        if (actualClientId == null || actualClientId.isEmpty()) {
            messageWindow.showErrorMessage("Error", "Client ID is empty");
            return;
        }
        else if (!Services.getClientSearcher().userExists(actualClientId)) {
            messageWindow.showErrorMessage("Error", "Client does not exist");
            return;
        }
        messageWindow.showSuccessMessage("Success", "Client loaded");
        loadClientProducts(clientId.getText());
    }
}

