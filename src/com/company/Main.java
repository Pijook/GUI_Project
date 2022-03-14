package com.company;

import com.company.container.Container;
import com.company.container.ContainerController;
import com.company.ship.Ship;
import com.company.ship.ShipController;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static ShipController shipController;
    private static ContainerController containerController;

    public static void main(String[] args) throws IOException {
        loadData();

        Scanner scanner = new Scanner(System.in);

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
        }

        saveData();
    }

    private static void displayMainMenu(){
        System.out.println("Choose option:");
        System.out.println("1. Create new ship");
        System.out.println("2. Create new container");
        System.out.println("3. Load container on ship");
        System.out.println("4. Unload container from ship");
        System.out.println("5. Show ships");
        System.out.println("6. Show containers");
        System.out.println("0. Exit");
    }

    private static void loadData() throws IOException {
        shipController = new ShipController();
        containerController = new ContainerController();

        shipController.loadShips();
        containerController.loadContainers();
    }

    private static void saveData() throws IOException {

        shipController.saveShips();
        containerController.saveContainers();

    }

    public static ContainerController getContainerController() {
        return containerController;
    }

    public static ShipController getShipController() {
        return shipController;
    }
}
