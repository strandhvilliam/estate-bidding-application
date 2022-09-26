module com.strandhvilliam.estatebiddingapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.strandhvilliam.estatebiddingapplication to javafx.fxml;
    exports com.strandhvilliam.estatebiddingapplication;
}