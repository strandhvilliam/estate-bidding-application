package com.strandhvilliam.estatebiddingapplication;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private BorderPane mainWindowBorderPane;
    @FXML
    private Label addressLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label askingPriceLabel;
    @FXML
    private Label highestBidLabel;

    @FXML
    private Label currentHighestBidLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label areaLabel;
    @FXML
    private Label hasBalconyLabel;
    @FXML
    private Label floorLabel;
    @FXML
    private Label amountOfRoomsLabel;
    @FXML
    private ImageView estateImageView;
    @FXML
    private ListView<Estate> estatesListView;
    @FXML
    private TableView<Bid> biddingHistoryTableView;
    @FXML
    private TableColumn<Bid, String> nameColumn;
    @FXML
    private TableColumn<Bid, String> amountColumn;
    @FXML
    private ContextMenu listContextMenu;

    private final ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    private final ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    private final ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
    private final ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);


    @FXML
    private void addNewEstateEvent(ActionEvent event) {

        ButtonType[] buttonTypes = {addButtonType, cancelButtonType};
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new_estate_dialog.fxml"));
            Dialog<ButtonType> dialog = initDialog("Add new estate", fxmlLoader, buttonTypes);
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == addButtonType) {
                NewEstateDialogController controller = fxmlLoader.getController();

                Estate newEstate = controller.processResults();
                Data.getInstance().addEstate(newEstate);
                estatesListView.getSelectionModel().select(newEstate);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Couldn't load the dialog");
            displayAlert("Error",e.getMessage());
        }
    }



    @FXML
    public void addNewBidEvent(ActionEvent event) {
        Estate selectedEstate = estatesListView.getSelectionModel().getSelectedItem();
        if (selectedEstate == null) {
            displayAlert("No estate selected", "Please select an estate to add a bid to");
            return;
        } else if (selectedEstate.isSold()) {
            displayAlert("Estate is already sold", "Can not add bid to an already sold estate" );
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new_bid_dialog.fxml"));
            Dialog<ButtonType> dialog = initDialog("Add new bid", fxmlLoader, new ButtonType[]{addButtonType, cancelButtonType});
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == addButtonType) {
                NewBidDialogController controller = fxmlLoader.getController();
                selectedEstate.addBid(controller.processResults());
                biddingHistoryTableView.setItems(getBiddingHistoryObservableList(selectedEstate));
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Couldn't load the dialog");
            System.out.println(e.getMessage());
            return;
        }
        updateEstateInformation(selectedEstate);
    }

    @FXML
    public void endBiddingProcessEvent(ActionEvent event) {

        if (estatesListView.getSelectionModel().getSelectedItem() == null) {
            displayAlert("No estate selected", "Please select an estate to end the bidding process for");
            return;
        } else if (estatesListView.getSelectionModel().getSelectedItem().isSold()) {
            displayAlert("Estate is already sold", "Estate is already sold");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("End bidding process");
        confirm.setHeaderText("Are you sure you want to end the bidding process?");
        confirm.getDialogPane().getStylesheets().add(getClass().getResource("dialogstyles.css").toExternalForm());
        confirm.getButtonTypes().clear();
        confirm.getButtonTypes().addAll(okButtonType, cancelButtonType);
        confirm.getDialogPane().lookupButton(okButtonType).setId("okButton");
        confirm.getDialogPane().lookupButton(cancelButtonType).setId("cancelButton");
        confirm.showAndWait();
        if (confirm.getResult() != okButtonType) {
            return;
        }

        Estate selectedEstate = estatesListView.getSelectionModel().getSelectedItem();
        if (selectedEstate.hasBid()) {
            selectedEstate.markAsSold();
            updateEstateInformation(selectedEstate);
        } else {
            displayAlert("No bids on this estate", "No bids on this estate");
        }


    }

    @FXML
    public void showUnsoldEstatesEvent(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("unsold_estates_dialog.fxml"));
            Dialog<ButtonType> dialog = initDialog("Unsold estates", fxmlLoader, new ButtonType[]{closeButtonType});
            dialog.showAndWait();

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }
    }

    @FXML
    public void showSoldEstatesEvent(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sold_estates_dialog.fxml"));
            Dialog<ButtonType> dialog = initDialog("Sold estates", fxmlLoader, new ButtonType[]{closeButtonType});
            dialog.showAndWait();

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }
    }

    public ObservableList<Bid> getBiddingHistoryObservableList(Estate estate) {
        ObservableList<Bid> biddingHistory = FXCollections.observableArrayList();
        biddingHistory.addAll(estate.getBids());
        return biddingHistory;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(actionEvent -> {
            Estate selectedEstate = estatesListView.getSelectionModel().getSelectedItem();
            Data.getInstance().getEstates().remove(selectedEstate);
            estatesListView.setItems(Data.getInstance().getEstates());
            if (Data.getInstance().getEstates().size() > 0) {
                estatesListView.getSelectionModel().selectFirst();
            } else {
                updateEstateInformation(new Estate("ADDRESS", "LOCATION", 0L, "TYPE", 0, false, 0, 0, null));
            }
        });

        listContextMenu = new ContextMenu();
        listContextMenu.setId("listContextMenu");
        listContextMenu.getItems().addAll(deleteMenuItem);

        estatesListView.setItems(Data.getInstance().getEstates());
        estatesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        estatesListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    if (newValue != null) {
                        updateEstateInformation(newValue);
                    }
                }
        );
        estatesListView.getSelectionModel().selectFirst();

    }


    public void updateEstateInformation(Estate estate) {
        addressLabel.setText(estate.getAddress());
        locationLabel.setText(estate.getLocation());
        typeLabel.setText(estate.getType());
        DecimalFormat df = new DecimalFormat("###,###,###");
        askingPriceLabel.setText(df.format(estate.getAskingPrice()));
        if (estate.hasBid()) {
            highestBidLabel.setText(df.format(estate.getHighestBid().getAmount()));
        } else {
            highestBidLabel.setText("No bids");
        }

        if (estate.isSold()) {
            currentHighestBidLabel.setText("Sold for");
        } else {
            currentHighestBidLabel.setText("Current highest bid");
        }

        areaLabel.setText(estate.getArea() + "m\u00B2");
        hasBalconyLabel.setText((estate.hasBalcony() ? "Yes" : "No"));
        floorLabel.setText(String.valueOf(estate.getFloor()));
        amountOfRoomsLabel.setText(String.valueOf(estate.getAmountOfRooms()));

        try {
            estateImageView.setImage(new Image(estate.getImageFile().toURI().toString()));
        } catch (NullPointerException e) {
            File placeHolder = new File("src/main/resources/com/strandhvilliam/estatebiddingapplication/images/placeholderEstate.png");
            estateImageView.setImage(new Image(placeHolder.toURI().toString()));
        }

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amountAsString"));
        estatesListView.setContextMenu(listContextMenu);

        if (estate.hasBid()) {
            biddingHistoryTableView.setItems(getBiddingHistoryObservableList(estate));
        } else {
            biddingHistoryTableView.setItems(null);
        }
    }

    public Dialog<ButtonType> initDialog(String title, FXMLLoader fxmlLoader, ButtonType[] buttons) throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindowBorderPane.getScene().getWindow());
        dialog.setTitle(title);
        dialog.getDialogPane().setContent(fxmlLoader.load());

        for (ButtonType button : buttons) {
            dialog.getDialogPane().getButtonTypes().add(button);
            dialog.getDialogPane().lookupButton(button).setId(button.getText().toLowerCase() + "Button");
        }

        try {
            dialog.getDialogPane().getStylesheets().add((getClass().getResource("dialogstyles.css")).toString());
        } catch (NullPointerException e) {
            System.out.println("Couldn't find the css file");
        }
        return dialog;
    }

    public void displayAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("dialogstyles.css").toExternalForm());
        alert.showAndWait();
    }
}