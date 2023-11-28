package com.bankworksystem.bankworksystem.frameworks.UI;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Gender;
import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.ProductType;
import com.bankworksystem.bankworksystem.frameworks.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Set;

public class seeAllClientsController {

    @FXML
    private TableView<Client> tableClient;

    @FXML
    private TableColumn<Client, String> columnID;

    @FXML
    private TableColumn<Client, String> columnName;

    @FXML
    private TableColumn<Gender, String> columnGender;

    @FXML
    private ChoiceBox<String> gender;

    @FXML
    private ImageView pricipalWindow;

    @FXML
    private ChoiceBox<String> products;

    @FXML
    private ImageView returnWindow;

    @FXML
    private TextField searchByName;

    private String[] searchForGender;
    private String[] searchForProduct;

    private ObservableList<Client> clients = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up the table columns
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        // Populate the table with data
        clients.addAll(Services.getClientSearcher().getClients());
        tableClient.setItems(clients);
        Gender[] genders = Gender.values();
        String[] genderNames = new String[genders.length];

        for (int i = 0; i < genderNames.length; i++) {
            genderNames[i] = genders[i].getGenderName();
        }
        gender.getItems().addAll(genderNames);

        ProductType[] productTypes = ProductType.values();
        String[] productTypeNames = new String[productTypes.length];

        for (int i = 0; i < productTypeNames.length; i++) {
            productTypeNames[i] = productTypes[i].getName();
        }
        products.getItems().addAll(productTypeNames);
    }

    @FXML
    private void searchByName(ActionEvent event) {
        String name = searchByName.getText();
        //filtered table
        Set<Client> clientsByName = Services.getClientSearcher().getClientsByName(name);
        ObservableList<Client> filteredClients = FXCollections.observableArrayList(clientsByName);
        //table refresh
        tableClient.setItems(filteredClients);
    }

    @FXML
    private void searchForGender(ActionEvent event){

        String selectedGender = gender.getValue();
        // Convert the selected gender to the Gender enum (assuming you have such conversion logic)
        Gender gender = Gender.getGenderByName(selectedGender);
        Set<Client> clientsByGender = Services.getClientSearcher().getClientsByGender(Gender.getGenderByName(selectedGender));
        ObservableList<Client> filteredClients = FXCollections.observableArrayList(clientsByGender);
        tableClient.setItems(filteredClients);
    }

    @FXML
    private void searchForProduct(ActionEvent event) {
        System.out.println("Me estoy ejecutando");
        // Get the selected product type
        String selectedProductType = Services.getProductSearcher().getProductName().toString();

        if (selectedProductType != null && !selectedProductType.isEmpty()) {
            // Filtered table
            /*Set<Product> productsByType = Services.getProductSearcher().getProductsByType(ProductType.getProductType(selectedProductType));
            ObservableList<Product> filteredProducts = FXCollections.observableArrayList(productsByType);
             //Table refresh
            tableClient.setItems(filteredProducts);*/
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error! PRODUCT NOT FOUND.");
            alert.showAndWait();
        }
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
            String fxml = "clientWindow.fxml";
            Node sourceNode = (Node) event.getSource();
            Navigation navigation = Navigation.getInstance();
            navigation.navigateToRemplaceScene("/com/bankworksystem/bankworksystem/" + fxml, sourceNode);
        });
    }
}
