module com.bankworksystem.bankworksystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bankworksystem.bankworksystem to javafx.fxml;
    exports com.bankworksystem.bankworksystem;
}