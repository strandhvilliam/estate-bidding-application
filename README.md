# estate-bidding-application

Simple etate bidding application that allows users to add estates and register bids.

If you want to try the program, go in 'out/artifacts..' folder and download the .jar file.

## Description

A few months ago as a school assignment I made an command-line application that would allow users to add estates and register bids. My goal with this project is to further develop that command-line app to a more robust desktop application and with that also learn and practice new techniques. Therefore I try to use the Java Collections framework to handle data, Serialization to save the data and JavaFX for the user to have a GUI to interact with. There is still a lot of ways that I want to improve the application, but since I haven't programmed for many months I am still pretty happy with it as I've learned many new concepts along the way.

## Requirements

- Java JDK (developed with jdk 17)
- JavaFX SDK (developed with sdk 17)
- Apache Maven (implemented using IntelliJ)

## Features

### Main Window

This is the primary window that the user interacts with.

![image](https://user-images.githubusercontent.com/87245022/192364974-b868e147-015c-4650-a985-115e01aa4b56.png)

There are multiple actions to pe performed here:
- Pressing the **Add New Estate Button** will prompt the user to enter address, asking price and other information and a new estate will be added to the list.
- Using the leftmost **estate list** will change the information in the window accordingly.
- The **show all sold/unsold estates button** will display a table containing data on estates that are sold or unsold respectively.
- Pressing the **Add New Bid** button will prompt the user to enter a name and amount to register a bid on the selected estate.
- The **End Bidding** button will end the bidding process and sell estate to the highest bidder.
- By **right clicking** users can delete the selected estate.
- The **Bidding History tab** will show all bids on currently selected estate (see image below).

![image](https://user-images.githubusercontent.com/87245022/192446309-15f8dd41-9832-4358-91a0-c0dd0e3a1479.png)

## Possible future improvements

- [ ] Better input respone when adding a new estate. Example: red when typing a string in a field that expects integer.
- [ ] Possible to edit estate information by right clicking.
- [ ] Darkmode??
- [x] Jar executable? Currently only runnable through IDE.
