package com.strandhvilliam.estatebiddingapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;

public class NewEstateDialogController {

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField areaTextField;

    @FXML
    private TextField askingPriceTextField;

    @FXML
    private CheckBox balconyCheckBox;

    @FXML
    private Button chooseImageButton;

    @FXML
    private TextField floorTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField roomsTextField;

    @FXML
    private TextField typeTextField;

    private File imageFile;

    public Estate processResults() {

        String address = validateAddressInput();
        String location = validateLocationInput();
        long askingPrice = validateAskingPriceInput();
        String type = validateTypeInput();
        boolean balcony = balconyCheckBox.isSelected();
        int floor = validateFloorsInput();
        int rooms = validateRoomsInput();
        int area = validateAreaInput();

        return new Estate(address, location, askingPrice, type, floor, balcony, area, rooms, imageFile);
    }


    public void chooseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedPath;
        selectedPath = fileChooser.showOpenDialog(null);

        if(selectedPath != null) {
            setImageFile(selectedPath);
        } else {
            System.out.println("No file selected");
        }
    }

    public String validateAddressInput() {

        String address = addressTextField.getText().trim();
        if(address.isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        } else if (address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be longer than 30 characters");
        }
        return address;
    }

    public long validateAskingPriceInput() {

        long askingPrice;
        try {
            askingPrice = Long.parseLong(askingPriceTextField.getText().trim());
            if(askingPrice <= 0) {
                throw new IllegalArgumentException("Asking price cannot be negative or zero");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Asking price must be a number");
        }
        return askingPrice;
    }


    public int validateAreaInput() {
        int area;
        try {
            area = Integer.parseInt(areaTextField.getText().trim());
            if(area <= 0) {
                throw new IllegalArgumentException("Area cannot be negative or zero");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Area must be a number");
        }
        return area;
    }

    public int validateFloorsInput() {

        int floors;
        try {
            floors = Integer.parseInt(floorTextField.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Floor must be a number");
        }
        return floors;
    }

    public String validateLocationInput() {
        String location = locationTextField.getText().trim();
        if(location.isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty");
        } else if (location.length() > 25) {
            throw new IllegalArgumentException("Location cannot be longer than 25 characters");
        }
        return location;
    }

    public int validateRoomsInput() {
        int rooms;
        try {
            rooms = Integer.parseInt(roomsTextField.getText().trim());
            if(rooms <= 0) {
                throw new IllegalArgumentException("Rooms cannot be negative or zero");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rooms must be a number");
        }
        return rooms;
    }

    public String validateTypeInput() {
        String type = typeTextField.getText().trim();
        if(type.isEmpty()) {
            throw new IllegalArgumentException("Type cannot be empty");
        } else if (type.length() > 15) {
            throw new IllegalArgumentException("Type cannot be longer than 15 characters");
        }
        return type;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}