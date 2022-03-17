package com.company;

import com.company.container.ContainerController;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.ship.ShipController;
import com.company.train.Train;
import com.company.warehouse.Warehouse;

import java.io.IOException;

public class Main {

    private static ShipController shipController;
    private static ContainerController containerController;
    private static PortTime portTime;
    private static Warehouse warehouse;
    private static Train train;

    private static Menu mainMenu;

    public static void main(String[] args) {
        loadData();

        portTime.start();

        mainMenu.open();

        saveData();
    }

    private static void setupMenu(){
        mainMenu = new Menu("Main Menu");

        mainMenu.addOption(1, new Option("Create new ship", () -> {
            shipController.openShipCreator();
        }, false));

        mainMenu.addOption(2, new Option("Create new container", () -> {
            containerController.openCreateContainerMenu();
        }, false));

        mainMenu.addOption(3, new Option("Show ships", () -> {
            shipController.openShipList();
        }, false));

        mainMenu.addOption(4, new Option("Manage warehouse", () -> {
            warehouse.openWarehouseMenu();
        }, false));

        mainMenu.addOption(5, new Option("Show current date", () -> {
            portTime.showCurrentDate();
        }, false));

        mainMenu.addOption(6, new Option("Save", Main::saveData, false));
    }

    private static void loadData() {
        shipController = new ShipController();
        containerController = new ContainerController();

        try {
            portTime = new PortTime();
        } catch (IOException e) {
            System.out.println("Couldn't load port time!");
        }

        warehouse = new Warehouse(2000);
        train = new Train();

        try {
            shipController.loadShips();
        } catch (IOException e) {
            System.out.println("Couldn't load ships!");
        }

        containerController.loadContainers();
        setupMenu();
    }

    private static void saveData(){

        try{
            containerController.saveStoredContainers();
            containerController.saveShippedContainers();
            shipController.saveShips();
            portTime.savePortTime();

            portTime.interrupt();
            train.interrupt();
        }
        catch (IOException e){
            e.printStackTrace();
        }

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
}
