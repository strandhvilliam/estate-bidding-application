package com.strandhvilliam.estatebiddingapplication;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowUnsoldEstatesDialogController implements Initializable {
    @FXML
    private TableView<Estate> unsoldTableView;

    @FXML
    private TableColumn<Estate, String> unsoldAddressColumn;

    @FXML
    private TableColumn<Estate, Long> unsoldBidColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        unsoldAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        unsoldBidColumn.setCellValueFactory(new PropertyValueFactory<>("askingPrice"));

        for (Estate estate : Data.getInstance().getEstates()) {
            if (!estate.isSold()) {
                unsoldTableView.getItems().add(estate);
            }
        }
    }
}
