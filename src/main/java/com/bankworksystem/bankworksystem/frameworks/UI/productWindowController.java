package com.bankworksystem.bankworksystem.frameworks.UI;
import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.CDT;
import com.bankworksystem.bankworksystem.entities.products.ProductType;
import com.bankworksystem.bankworksystem.entities.products.UninitializedProduct;
import com.bankworksystem.bankworksystem.frameworks.Services;
import com.bankworksystem.bankworksystem.useCases.ProductModificationService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class productWindowController {

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

    public TextField clientId;

    private boolean productLoaded = false;
    private String actualClientId;
    private MessageWindow messageWindow;
    private HashMap<String, Product> actualProducts = new HashMap<>();
    private Product selectedProduct;

    @FXML
    private void initialize() {
        messageWindow = new MessageWindow();

        create.setOnAction(e -> onCreateButtonClicked());
        clientId.setOnAction(e -> onClientIdEntered());
        seachByID.setOnAction(e -> onClientIdEntered());
        searchProductOfClient.setOnAction(e -> onProductSelected());
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
        if (selectedProduct instanceof UninitializedProduct) {
            initializeProduct((UninitializedProduct) selectedProduct);
        } else {
            modifyProduct(selectedProduct);
        }
    }

    private void modifyProduct(Product product) {
        String productId = product.getId();
        ProductType productType = ProductType.getProductType(product);

        ProductModificationService productModificationService = Services.getProductModificationService();

        if (productType == ProductType.CDT)
            productModificationService.modifyCDTTimePeriod(productId, Integer.parseInt(termInMonths.getText()));

        productModificationService.modifyProductBalance(productId, Double.parseDouble(balance.getText()));
        productModificationService.modifyProductOpeningDate(productId, convertLocalDateToDate(date.getValue()));
    }

    private void initializeProduct(UninitializedProduct product) {
        ProductType productType = product.getProductType();

        switch (productType) {
            case CDT:
                Services.getProductCreationService().initializeCDT(product.getId(), convertLocalDateToDate(date.getValue()), Integer.parseInt(termInMonths.getText()));
                break;
            case AMERICAN_EXPRESS:
            case VISA_CARD:
            case MASTERCARD:
                Services.getProductCreationService().initializeCard(product.getId(), convertLocalDateToDate(date.getValue()));
                break;
            case SAVINGS_ACCOUNT:
                Services.getProductCreationService().initializeAccount(product.getId(), convertLocalDateToDate(date.getValue()));
                break;
        }
    }

    public Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }


    private void loadClientProducts(String clientId) {

        Set<Product> productList = Services.getProductSearcher().getProductsByUniqueOwner(clientId);

        String[] productNames = new String[productList.size()];

        int i = 0;
        for (Product product : productList) {
            ProductType productType = ProductType.getProductType(product);
            String productName = Objects.equals(productType.getName(), ProductType.UninitializedProduct.getName()) ?
                    ((UninitializedProduct) product).getProductType().getName() : productType.getName();
            productNames[i] = productName;
            actualProducts.put(productName, product);
            i++;
        }

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

    private void onProductSelected() {
        String selectedProduct = (String) searchProductOfClient.getValue();
        this.selectedProduct = actualProducts.get(selectedProduct);
        if (this.selectedProduct instanceof UninitializedProduct) {
            messageWindow.showSuccessMessage("Alert", "This product is not initialized");
            create.setText("Create product");
        } else {
            create.setText("Modify product");
        }
        updateProductSpecificUI(this.selectedProduct);
    }

    private void updateProductSpecificUI(Product product) {

        termInMonths.setDisable(true);

        if (product instanceof CDT)
            termInMonths.setDisable(false);
    }
}

