package com.strandhvilliam.estatebiddingapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This singleton class is used to store and retrieve data from the application.
 */
public class Data {
    private static final Data instance = new Data();
    private static final String filename = "estates.ser";
    private ObservableList<Estate> estates = FXCollections.observableArrayList();
    private List<Estate> estatesList;
    public static Data getInstance() {
        return instance;
    }
    public ObservableList<Estate> getEstates() {
        return estates;
    }

    public void addEstate(Estate estate) {
        estates.add(estate);
    }
    public void deleteEstate(Estate estate) {
        estates.remove(estate);
    }

    public void loadEstates() {
        estatesList = new ArrayList<>();
        Path path = Paths.get(filename);
        try (FileInputStream fileIn = new FileInputStream(path.toString())) {
            ObjectInputStream in = new ObjectInputStream(fileIn);
            estatesList = (ArrayList<Estate>) in.readObject();
            estates = FXCollections.observableArrayList(estatesList);
            System.out.println("Loaded estates from file");
            in.close();
        } catch (EOFException e) {
            System.out.println("No estates in file");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public void storeEstates() {
        Path path = Paths.get(filename);
        try (FileOutputStream fileOut = new FileOutputStream(path.toString())) {
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            estatesList = new ArrayList<>(estates);
            out.writeObject(estatesList);
            System.out.println("Serialized data is saved in " + path);
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}