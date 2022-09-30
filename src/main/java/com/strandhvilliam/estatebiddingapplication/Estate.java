package com.strandhvilliam.estatebiddingapplication;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Estate implements Serializable {

    private String address;
    private final String location;
    private final Long askingPrice;
    private final String type;
    private final int floor;
    private final boolean balcony;
    private final int area;
    private final int rooms;
    private boolean sold;
    private Long winningBid;
    private final List<Bid> bids;
    private File imageFile;

    public Estate(String address, String location, Long askingPrice, String type , int floor, boolean balcony, int area, int rooms, File imageFile) {
        this.address = address;
        this.location = location;
        this.askingPrice = askingPrice;
        this.type = type;
        this.floor = floor;
        this.balcony = balcony;
        this.area = area;
        this.rooms = rooms;
        this.sold = false;
        this.imageFile = imageFile;
        this.bids = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public Long getAskingPrice() {
        return askingPrice;
    }

    public String getType() {
        return type;
    }

    public int getFloor() {
        return floor;
    }

    public boolean hasBalcony() {
        return balcony;
    }

    public int getArea() {
        return area;
    }


    public int getAmountOfRooms() {
        return rooms;
    }


    public boolean isSold() {
        return sold;
    }

    public void markAsSold() {
        this.winningBid = getHighestBid().getAmount();
        this.sold = true;
        System.out.println("Marked as sold");
    }

    public long getWinningBid() {
        return winningBid;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public void removeBid(Bid bid) {
        this.bids.remove(bid);
    }

    public File getImageFile() {
        return imageFile;
    }
    public void setImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        imageFile = fileChooser.showOpenDialog(null);
    }

    public Bid getHighestBid() {
        Bid highestBid = null;
        for (Bid bid : bids) {
            if (highestBid == null || bid.getAmount() > highestBid.getAmount()) {
                highestBid = bid;
            }
        }
        return highestBid;
    }

    public boolean hasBid() {
        return !bids.isEmpty();
    }

    @Override
    public String toString() {
        return String.format(getAddress());
    }

}