package com.strandhvilliam.estatebiddingapplication;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowSoldEstatesDialogController implements Initializable {

    @FXML
    private TableView<Estate> soldTableView;

    @FXML
    private TableColumn<Estate, String> soldAddressColumn;

    @FXML
    private TableColumn<Bid, Long> soldBidColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        soldAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        soldBidColumn.setCellValueFactory(new PropertyValueFactory<>("winningBid"));

        for (Estate estate : Data.getInstance().getEstates()) {
            if (estate.isSold()) {
                soldTableView.getItems().add(estate);
            }
        }
    }
}

