module com.bankworksystem.bankworksystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bankworksystem.bankworksystem to javafx.fxml;
    exports com.bankworksystem.bankworksystem;
    exports com.bankworksystem.bankworksystem.frameworks.persistency.UI;
    opens com.bankworksystem.bankworksystem.frameworks.persistency.UI to javafx.fxml;
}