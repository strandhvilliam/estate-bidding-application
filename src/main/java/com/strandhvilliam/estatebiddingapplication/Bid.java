package com.strandhvilliam.estatebiddingapplication;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Bid implements Serializable {

    private final String name;
    private final long amount;

    private final String amountAsString;

    public Bid(String name, long amount) {
        this.name = name;
        this.amount = amount;
        DecimalFormat df = new DecimalFormat("###,###,###");
        this.amountAsString = df.format(amount);
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public String getAmountAsString() {
        return amountAsString;
    }

}
