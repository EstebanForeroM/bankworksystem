package com.bankworksystem.bankworksystem.frameworks.UI;

import com.bankworksystem.bankworksystem.entities.Product;
import com.bankworksystem.bankworksystem.entities.products.ProductType;
import com.bankworksystem.bankworksystem.entities.products.UninitializedProduct;
import com.bankworksystem.bankworksystem.frameworks.Services;
import com.bankworksystem.bankworksystem.useCases.Token;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.bankworksystem.bankworksystem.frameworks.UI.validations.validatePassword;
import static com.bankworksystem.bankworksystem.frameworks.UI.validations.validateTransactionAmount;

public class transferenstController {

    @FXML
    private Button advance;

    @FXML
    private Button balance;

    @FXML
    private Button buy;

    @FXML
    private Button deposit;

    @FXML
    private Button payments;

    @FXML
    private ImageView pricipalWindow;

    @FXML
    private ImageView returnWindow;

    @FXML
    private Button shangePassword;

    @FXML
    private Button withdrawals;

    @FXML
    private ChoiceBox<ProductType> typeOfProducts;


    private ObservableList<ProductType> productTypes = FXCollections.observableArrayList(ProductType.values());


    private HashMap<String, Product> actualProducts = new HashMap<>();

    private String actualClientId;

    private Token userToken;

    private Product selectedProduct;

    @FXML
    private void initialize() {
        disableAllButtons();
        initializeClientTypes();
        userToken = passwordWindowController.getUserToken();
        loadClientProducts(userToken.getClientId());
        typeOfProducts.setOnAction(e -> updateSelectedProduct());
    }

    private void updateSelectedProduct() {
        String clientId = userToken.getClientId();
        ProductType productType = ProductType.getProductType(actualProducts.get(typeOfProducts.getValue()));

        Set<Product> products = Services.getProductSearcher().getProductsByUniqueOwner(clientId);

        if (productType == ProductType.UninitializedProduct) {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "The selected product is not initialized.\n Create the product first or select another one.");
        }

        for (Product product : products) {
            ProductType productType1 = ProductType.getProductType(product);

            if (productType1 == productType) {
                selectedProduct = product;
                break;
            }
        }

        initializeButtonsForTypeOfProducts(productType);
    }

    private void loadClientProducts(String clientId) {

        Set<Product> productList = Services.getProductSearcher().getProductsByUniqueOwner(clientId);

        String[] productNames = new String[productList.size()];

        int i = 0;
        String productName = null;
        for (Product product : productList) {
            ProductType productType = ProductType.getProductType(product);
            productName = Objects.equals(productType.getName(), ProductType.UninitializedProduct.getName()) ?
                    ((UninitializedProduct) product).getProductType().getName() : productType.getName();
            productNames[i] = productName;
            actualProducts.put(productName, product);
            i++;
        }

        typeOfProducts.getItems().clear();
        typeOfProducts.getItems().addAll(ProductType.valueOf(productName));
    }

    private void initializeClientTypes() {
        userToken = passwordWindowController.getUserToken();
    }

    private void disableAllButtons() {
        buy.setDisable(true);
        payments.setDisable(true);
        balance.setDisable(true);
        advance.setDisable(true);
        deposit.setDisable(true);
        withdrawals.setDisable(true);
    }

    private void enableButtons(Button... buttons) {
        for (Button button : buttons) {
            button.setDisable(false);
        }
    }

    private void initializeButtonsForTypeOfProducts(ProductType productType) {
        disableAllButtons();
        switch (productType) {
            case CDT:
                break;
            case CHECKING_ACCOUNT:
                enableButtons(withdrawals, deposit, balance);
                break;
            case VISA_CARD:
                enableButtons(buy, payments, balance, advance);
                break;
            case AMERICAN_EXPRESS:
                enableButtons(buy, payments, balance, advance);
                break;
            case SAVINGS_ACCOUNT:
                enableButtons(withdrawals, deposit, balance);
                break;
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
    private void buttonAdvance(ActionEvent event) {
        Optional<String> result = showTextInputDialog("Enter the amount to advance", "Advance", "Please enter the amount to advance:");
        result.ifPresent(amount -> {
            try
            {
                Double amountDouble = Double.parseDouble(amount);
                String productId = actualProducts.get(typeOfProducts.getValue()).getId();
                Services.getTransactionService().withdraw(userToken,productId, amountDouble);
                updateSelectedProduct();
                MessageWindow messageWindow = new MessageWindow();
                messageWindow.showSuccessMessage("Information", "Your advance has been made successfully.");
            }
            catch (Exception e) {
                MessageWindow messageWindow = new MessageWindow();
                messageWindow.showErrorMessage("Error", e.getMessage());
            }
        });
    }

    @FXML
    private void buttonBalance(ActionEvent event) {
        MessageWindow messageWindow = new MessageWindow();
        messageWindow.showSuccessMessage("Information", "Your balance is: " + selectedProduct.getBalance());
    }


    @FXML
    private void buttonBuy(ActionEvent event) {
        Optional<String> result = showTextInputDialog("Enter the amount to buy", "Buy", "Please enter the amount to buy:");

        if (result.isPresent() && !result.get().isEmpty()) {
            try {
                double buyAmount = Double.parseDouble(result.get());
                if (validateTransactionAmount(buyAmount)) {
                    String productId = selectedProduct.getId();
                    Services.getTransactionService().withdraw(userToken,productId, buyAmount);
                    updateSelectedProduct();
                    MessageWindow messageWindow = new MessageWindow();
                    messageWindow.showSuccessMessage("Information", "Your buy has been made successfully.");
                } else {
                    MessageWindow messageWindow = new MessageWindow();
                    messageWindow.showErrorMessage("Error", "Invalid buy amount. Please enter a valid amount.");
                }
            } catch (NumberFormatException e) {
                MessageWindow messageWindow = new MessageWindow();
                messageWindow.showErrorMessage("Error", e.getMessage());
            }
        } else {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "Please enter a valid amount to buy.");
        }
    }

    @FXML
    private void buttonDeposit(ActionEvent event) {
        Optional<String> result = showTextInputDialog("Enter the amount to deposit", "Deposit", "Please enter the amount to deposit:");

        if (result.isPresent() && !result.get().isEmpty()) {
            try {
                double depositAmount = Double.parseDouble(result.get());
                if (validateTransactionAmount(depositAmount)) {
                    Services.getTransactionService().deposit(userToken,selectedProduct.getId(), depositAmount);
                    updateSelectedProduct();
                } else {
                    MessageWindow messageWindow = new MessageWindow();
                    messageWindow.showErrorMessage("Error", "Invalid deposit amount. Please enter a valid amount.");
                }
            } catch (NumberFormatException e) {
                MessageWindow messageWindow = new MessageWindow();
                messageWindow.showErrorMessage("Error", e.getMessage());
            }
        } else {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "Please enter a valid amount to deposit.");
        }
    }

    @FXML
    private void buttonPayments(ActionEvent event) {
        Optional<String> result = showTextInputDialog("Enter the amount to pay", "Pay", "Please enter the amount to pay:");

        if (result.isPresent() && !result.get().isEmpty()) {
            try {
                double paymentAmount = Double.parseDouble(result.get());
                if (validateTransactionAmount(paymentAmount)) {
                    String productId = selectedProduct.getId();
                    Services.getTransactionService().deposit(userToken,productId, paymentAmount);
                    updateSelectedProduct();
                    MessageWindow messageWindow = new MessageWindow();
                    messageWindow.showSuccessMessage("Information", "Your payment has been made successfully.");
                } else {
                    MessageWindow messageWindow = new MessageWindow();
                    messageWindow.showErrorMessage("Error", "Invalid payment amount. Please enter a valid amount.");
                }
            } catch (NumberFormatException e) {
                MessageWindow messageWindow = new MessageWindow();
                messageWindow.showErrorMessage("Error", "Invalid input. Please enter a valid number for the payment amount.");
            }
        } else {
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showErrorMessage("Error", "Please enter a valid amount to pay.");
        }
    }

    @FXML
    private void buttonShangePassword(ActionEvent event) {
        Optional<String> result = showTextInputDialog("Enter your new password", "Enter your new password", "Please enter your new password:");

        if (result.isPresent() && !result.get().isEmpty()) {
            String newPassword = result.get();
            if (validatePassword(newPassword)) {
                Token token = userToken;
                Services.getUserModificationService().modifyUserPassword(token, newPassword);
                MessageWindow messageWindow = new MessageWindow();
                messageWindow.showSuccessMessage("Information", "Your password has been changed successfully.");
            }
        }
    }

    @FXML
    private void buttonWithdrawals(ActionEvent event) {
        Optional<String> result = showTextInputDialog("Enter the amount to withdraw", "Withdraw", "Please enter the amount to withdraw:");
        result.ifPresent(amount -> {
            try {
                String productId = selectedProduct.getId();
                Services.getTransactionService().withdraw(userToken,productId, Double.parseDouble(amount));
            }   catch (Exception e) {
                MessageWindow messageWindow = new MessageWindow();
                messageWindow.showErrorMessage("Error", e.getMessage());
                return;
            }
            MessageWindow messageWindow = new MessageWindow();
            messageWindow.showSuccessMessage("Information", "Your withdrawal has been made successfully.");
            updateSelectedProduct();
        });
    }

    public static Optional<String> showTextInputDialog(String promptText, String title, String headerText) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setHeight(600);
        alert.setWidth(300);

        TextField textField = new TextField();
        textField.setPromptText(promptText);

        GridPane grid = new GridPane();
        grid.add(textField, 0, 0);

        alert.getDialogPane().setContent(grid);

        ButtonType okButton = new ButtonType("Save", ButtonType.OK.getButtonData());
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        return result.flatMap(buttonType -> {
            if (buttonType == okButton) {
                return Optional.ofNullable(textField.getText());
            } else {
                return Optional.empty();
            }
        });
    }
}
