package com.strandhvilliam.estatebiddingapplication;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewBidDialogController {

    @FXML
    private TextField amountTextField;

    @FXML
    private TextField nameTextField;

    public Bid processResults() {
        String name = validateNameInput();
        long amount = validateAmountInput();
        return new Bid(name, amount);
    }


    private String validateNameInput() {
        String name = nameTextField.getText();
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Name is required");
        }
        return name;
    }

    private long validateAmountInput() {
        String amount = amountTextField.getText();
        if (amount == null || amount.length() == 0) {
            throw new IllegalArgumentException("Amount is required");
        }
        try {
            return Long.parseLong(amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount must be a number");
        }
    }

}