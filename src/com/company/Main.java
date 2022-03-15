package com.company;

import com.company.container.ContainerController;
import com.company.container.exceptions.NotEnoughSpaceException;
import com.company.container.exceptions.TooManyDangerousContainersException;
import com.company.container.exceptions.TooManyElectricContainersException;
import com.company.container.exceptions.TooManyHeavyContainersException;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.ship.ShipController;

import java.io.IOException;

public class Main {

    private static ShipController shipController;
    private static ContainerController containerController;

    private static Menu mainMenu;

    public static void main(String[] args) throws IOException, NotEnoughSpaceException {
        loadData();

        /*Scanner scanner = new Scanner(System.in);

        int option = -1;
        while(option != 0){
            displayMainMenu();
            option = scanner.nextInt();
            switch (option){
                case 5 -> {
                    for(Ship ship : shipController.getShips()){
                        System.out.println(ship);
                    }
                }
                case 6 -> {
                    for(Container container : containerController.getContainers().values()){
                        System.out.println(container);
                    }
                }
            }
        }*/

        mainMenu.open();

        saveData();
    }

    /*private static void displayMainMenu(){
        System.out.println("Choose option:");
        System.out.println("1. Create new ship");
        System.out.println("2. Create new container");
        System.out.println("3. Load container on ship");
        System.out.println("4. Unload container from ship");
        System.out.println("5. Show ships");
        System.out.println("6. Show containers");
        System.out.println("0. Exit");
    }*/

    private static void setupMenu(){
        mainMenu = new Menu("Main Menu");

        mainMenu.addOption(1, new Option("Create new ship", () -> {

        }, true));

        mainMenu.addOption(2, new Option("Create new container", () -> {
            containerController.openCreateContainerMenu();
        }, true));

        mainMenu.addOption(3, new Option("Show ships", () -> {

        }, false));

        mainMenu.addOption(4, new Option("Show containers", () -> {
            containerController.openContainersListMenu();
        }, false));
    }

    private static void loadData() throws IOException, NotEnoughSpaceException {
        shipController = new ShipController();
        containerController = new ContainerController();

        shipController.loadShips();
        containerController.loadContainers();

        try {
            containerController.loadShippedContainers();
        } catch (TooManyHeavyContainersException e) {
            e.printStackTrace();
        } catch (TooManyDangerousContainersException e) {
            e.printStackTrace();
        } catch (TooManyElectricContainersException e) {
            e.printStackTrace();
        }

        setupMenu();
    }

    private static void saveData() throws IOException {

        shipController.saveShips();
        containerController.saveContainers();
        containerController.saveShippedContainers();

    }

    public static ContainerController getContainerController() {
        return containerController;
    }

    public static ShipController getShipController() {
        return shipController;
    }
}
