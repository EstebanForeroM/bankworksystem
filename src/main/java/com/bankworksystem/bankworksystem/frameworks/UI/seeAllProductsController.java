package com.bankworksystem.bankworksystem.frameworks.UI;

import com.bankworksystem.bankworksystem.entities.Client;
import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.ProductType;
import com.bankworksystem.bankworksystem.entities.products.UninitializedProduct;
import com.bankworksystem.bankworksystem.frameworks.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;

public class seeAllProductsController {

    @FXML
    public TableView<Product> tableView;

    @FXML
    private ChoiceBox<String> SearchByProducts;

    @FXML
    private TableColumn<Product, Double> columnBalance;

    @FXML
    private TableColumn<Product, String> columnDate;

    @FXML
    private TableColumn<Product, String> columnIDClient;

    @FXML
    private TableColumn<Product, String> columnIDProducts;

    @FXML
    private ImageView principalWindow;

    @FXML
    private ImageView returnWindow;

    @FXML
    private TextField searchByID;

    @FXML
    private ChoiceBox<String> searchIDAllClients;

    @FXML
    private TableColumn<Product, String> columnProducts;

    @FXML
    private ImageView selectedUserImages;

    private ObservableList<String> products;


    ObservableList<Product> productsList;
    FilteredList<Product> filteredList;

    @FXML
    private void initialize() {
        initializeProductsChoiceBox();
        initializeClientIdChoiceBox();
        setUpTable();
        searchByID.setOnKeyTyped(e -> verifySearchByID());
    }

    private void verifySearchByID() {
        String id = searchByID.getText();
        searchByID.setText(validations.validateClientID(id));
        searchByID.end();
    }

    private void setUpTable() {
        columnIDProducts.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnIDClient.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("openingDate"));
        columnBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        columnProducts.setCellValueFactory(new PropertyValueFactory<>("productName"));

        Set<Product> productSet = Services.getProductSearcher().getProducts();
        productsList = FXCollections.observableArrayList(productSet);
        filteredList = new FilteredList<Product>(productsList, p -> true);

        tableView.setItems(filteredList);

        initializeFilters();
    }

    private void initializeClientIdChoiceBox() {
        String[] clientIds = new String[Services.getClientSearcher().getClients().size()];
        int i = 0;
        for (Client client : Services.getClientSearcher().getClients()) {
            clientIds[i] = client.getId();
            i++;
        }

        searchIDAllClients.getItems().addAll(clientIds);
    }

    private void initializeProductsChoiceBox() {
        String[] products = new String[ProductType.values().length - 1];
        ProductType[] productsTypes = ProductType.values();

        for (int i = 0; i < products.length; i++) {
            if (productsTypes[i] == ProductType.UninitializedProduct) {
                i--;
                continue;
            }
            products[i] = productsTypes[i].getName();
        }

        SearchByProducts.getItems().addAll(products);
    }

    private void initializeFilters() {
        searchByID.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(product -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        searchIDAllClients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getOwnerId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SearchByProducts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                ProductType requiredProductType = ProductType.getProductType(newValue);
                ProductType productType;

                if (product instanceof UninitializedProduct)
                    productType = ((UninitializedProduct) product).getProductType();
                else
                    productType = ProductType.getProductType(product);

                if (requiredProductType.equals(productType)) {
                    return true;
                }
                return false;
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

    @FXML
    private void searchByID(){

    }
}