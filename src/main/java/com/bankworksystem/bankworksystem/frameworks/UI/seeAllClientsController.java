package com.bankworksystem.bankworksystem.frameworks.UI;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Gender;
import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.ProductType;
import com.bankworksystem.bankworksystem.entities.products.UninitializedProduct;
import com.bankworksystem.bankworksystem.frameworks.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Set;

public class seeAllClientsController {

    @FXML
    private TableView<Client> tableClient;

    @FXML
    private TableColumn<Client, String> columnID;

    @FXML
    private TableColumn<Client, String> columnName;

    @FXML
    private TableColumn<Client, Gender> columnGender;

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

    ObservableList<Client> clientList;
    FilteredList<Client> filteredList;

    private final ObservableList<Client> clients = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up the table columns
        setUpTable();
        setUpGenderChoiceBox();
        setUpProductsChoiceBox();
    }

    private void setUpProductsChoiceBox() {
        String[] products = new String[ProductType.values().length];
        ProductType[] productsTypes = ProductType.values();

        for (int i = 0; i < products.length; i++) {
            if (productsTypes[i] == ProductType.UninitializedProduct) {
                products[i] = "all";
                continue;
            }
            products[i] = productsTypes[i].getName();
        }

        this.products.getItems().addAll(products);
    }

    private void setUpGenderChoiceBox() {
        String[] gendersNames = new String[Gender.values().length + 1];
        int i = 0;
        for (Gender gender1 : Gender.values()) {
            gendersNames[i] = gender1.name();
            i++;
        }

        gendersNames[i] = "all";

        gender.getItems().addAll(gendersNames);
    }

    private void setUpTable() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        List<Client> clientList = Services.getClientSearcher().getClients();

        this.clientList = FXCollections.observableArrayList(clientList);
        filteredList = new FilteredList<Client>(this.clientList, p -> true);

        tableClient.setItems(filteredList);

        initializeFilters();
    }

    private void initializeFilters() {
        gender.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty() || newValue.equals("all")) {
                    return true;
                }

                if (client.getGender().name().equals(newValue)) {
                    return true;
                }

                return false;
            });
        });

        products.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty() || newValue.equals("all")) {
                    return true;
                }

                Set<Product> productSet = Services.getProductSearcher().getProductsByUniqueOwner(client.getId());

                for (Product product : productSet) {
                    ProductType productType;
                    if (product instanceof UninitializedProduct)
                        productType = ((UninitializedProduct) product).getProductType();
                    else
                        productType = ProductType.getProductType(product);

                    if (productType.getName().equals(newValue)) {
                        return true;
                    }
                }

                return false;
            });
        });

        searchByName.textProperty().addListener((observable, oldValue, newValue) -> {
            String validatedName = validations.validateName(newValue);
            if (validatedName == null) {
                searchByName.clear();
                return;
            }

            filteredList.setPredicate(client -> {
                if (validatedName.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = validatedName.toLowerCase();

                return client.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });
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
}
