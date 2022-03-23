package com.company;

import com.company.container.*;
import com.company.container.exceptions.NotEnoughSpaceException;
import com.company.container.exceptions.TooManyDangerousContainersException;
import com.company.container.exceptions.TooManyElectricContainersException;
import com.company.container.exceptions.TooManyHeavyContainersException;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.sender.Sender;
import com.company.sender.SenderController;
import com.company.sender.WarningController;
import com.company.ship.Ship;
import com.company.ship.ShipController;
import com.company.train.Train;
import com.company.warehouse.StoredContainer;
import com.company.warehouse.Warehouse;
import com.company.warehouse.exceptions.FullWarehouseException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    private static ShipController shipController;
    private static ContainerController containerController;
    private static SenderController senderController;
    private static WarningController warningController;
    private static PortTime portTime;
    private static Warehouse warehouse;
    private static Train train;

    private static Menu mainMenu;

    public static void main(String[] args) {
        loadData();

        mainMenu.open();

        saveData(true);
    }

    private static void setupMenu(){
        mainMenu = new Menu("Main Menu");

        mainMenu.addOption(1, new Option("Ships", () -> {
            shipController.openShipsMenu();
        }, false));

        mainMenu.addOption(2, new Option("Warehouse", () -> {
            warehouse.openWarehouseMenu();
        }, false));

        mainMenu.addOption(3, new Option("Senders", () -> {
            senderController.openSendersMenu();
        }, false));

        mainMenu.addOption(4, new Option("Train", () -> {
            train.openTrainMenu();
        }, false));

        mainMenu.addOption(5, new Option("Current date", () -> {
            portTime.showCurrentDate();
        }, false));

        mainMenu.addOption(6, new Option("Save", () -> {
            saveData(false);
        }, false));
    }

    private static void loadData() {
        shipController = new ShipController();
        containerController = new ContainerController();
        senderController = new SenderController();
        warningController = new WarningController();
        train = new Train(10);
        warehouse = new Warehouse(2000);
        portTime = new PortTime();

        try {
            shipController.loadShips();
            portTime.loadSavedTime();
            train.load();
            senderController.loadSenders();
            warningController.loadWarnings();
            containerController.loadContainers();

            portTime.start();
            warehouse.start();

            loadDemo();
        } catch (FullWarehouseException e) {
            System.out.println("Error occurred while loading stored containers! Warehouse reached max capacity!");
        } catch (NotEnoughSpaceException e) {
            System.out.println("Error occurred while loading shipped containers! Ship reached max capacity!");
        } catch (TooManyHeavyContainersException e) {
            System.out.println("Error occurred while loading shipped container! Ship reached max amount of heavy containers!");
        } catch (IOException e) {
            System.out.println("Error occurred while loading files! Couldn't create or find files!");
        } catch (TooManyDangerousContainersException e) {
            System.out.println("Error occurred while loading shipped container! Ship reached max amount of dangerous containers!");
        } catch (TooManyElectricContainersException e) {
            System.out.println("Error occurred while loading shipped container! Ship reached max amount of containers with electricity!");
        }

        setupMenu();
    }

    private static void saveData(boolean end){
        try{
            train.save();
            containerController.saveStoredContainers();
            containerController.saveShippedContainers();
            shipController.saveShips();
            portTime.savePortTime();
            senderController.saveSenders();
            warningController.saveWarnings();

            if(end){
                portTime.interrupt();
                train.interrupt();
                warehouse.interrupt();
            }
        }
        catch (IOException e){
            System.out.println("Error occurred while saving data! Couldn't create new file!");
        }

    }

    private static void loadDemo() throws FullWarehouseException, NotEnoughSpaceException, TooManyHeavyContainersException, TooManyDangerousContainersException, TooManyElectricContainersException {
        //Checking if first program run
        if(shipController.getShips().size() > 0 || senderController.getSenders().size() > 0 || warehouse.getStoredContainers().size() > 0){
            return;
        }

        ///Creating demo senders
        senderController.addSender(new Sender(
                "Adam",
                "Kowalski",
                "Warszawa KEN 11",
                "adamkowalski@gmail.com",
                "77042116376",
                new ArrayList<>()
        ));

        senderController.addSender(new Sender(
                "Ewa",
                "Zwyczajna",
                "Gdynia Niepodleglosci 17a",
                "ewazwyczajna@gmail.com",
                "82100254545",
                new ArrayList<>()
        ));

        senderController.addSender(new Sender(
                "Kamil",
                "Jalowiecki",
                "Krakow Prosta 17/5b",
                "kjalowiecki@wp.pl",
                "50081883111",
                new ArrayList<>()
        ));

        senderController.addSender(new Sender(
                "Oliwia",
                "Kulka",
                "Szczecin Krzywa 32",
                "oliwiakulka@post.pl",
                "40042406422",
                new ArrayList<>()
        ));

        //Creating demo containers
        Container container = new NormalContainer(2.0);
        container.setSenderID("40042406422");

        Container container1 = new CoolingContainer(5.0, "Gloves", 120.0);
        container1.setSenderID("50081883111");

        Container container2 = new HeavyContainer(10.0, "Belts");
        container2.setSenderID("40042406422");

        Container container3 = new ExplodingContainer(5.0, "Sunglasses", 50.0);
        container3.setSenderID("77042116376");

        Container container4 = new HazardousHeavyContainer(10.0, "Sunglasses", 5.0);
        container4.setSenderID("82100254545");

        //Storing containers
        warehouse.storeContainer(container1);
        warehouse.storeContainer(container2);


        //Creating demo ships
        shipController.addShip(new Ship(
                "Evergreen",
                "Gdynia Glowna",
                "Gdynia Glowna",
                "Los Angeles",
                25,
                500.0,
                10,
                10,
                5
        ));

        shipController.addShip(new Ship(
                "Titanic",
                "Los Angeles",
                "Los Angeles",
                "Atlantyda",
                5,
                50.0,
                1,
                1,
                5
        ));

        shipController.addShip(new Ship(
                "Ship",
                "Atlantyda",
                "Atlantyda",
                "Berlin",
                500,
                250.0,
                10,
                10,
                5
        ));

        shipController.addShip(new Ship(
                "Java",
                "Oracle",
                "Oracle",
                "Windows",
                999,
                20.0,
                19,
                1,
                5
        ));

        shipController.addShip(new Ship(
                "Stackoverflow",
                "Internet",
                "Internet",
                "Programmers",
                666,
                555.0,
                44,
                33,
                2
        ));

        //Shipping containers
        shipController.getShip("Stackoverflow").loadContainer(container3, true);
        shipController.getShip("Stackoverflow").loadContainer(container4, true);

    }

    public static ContainerController getContainerController() {
        return containerController;
    }

    public static ShipController getShipController() {
        return shipController;
    }

    public static PortTime getPortTime() {
        return portTime;
    }

    public static Warehouse getWarehouse() {
        return warehouse;
    }

    public static Train getTrain() {
        return train;
    }

    public static SenderController getSenderController() {
        return senderController;
    }

    public static WarningController getWarningController() {
        return warningController;
    }
}
