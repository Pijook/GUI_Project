package com.company.ship;

import com.company.container.Container;
import com.company.menu.Menu;
import com.company.menu.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ShipController {

    private final List<Ship> ships;

    public ShipController(){
        ships = new ArrayList<>();
    }

    public void openShipList(){
        Menu menu = new Menu("Ships");

        int i = 1;
        for(Ship ship : ships){
            menu.addOption(i, new Option(ship.getShipName(), () -> {
                openShipMenu(ship);
            }, false));
        }

        menu.open();
    }

    public void openShipMenu(Ship ship){
        Menu menu = new Menu("Ship " + ship.getShipName());

        menu.addOption(1, new Option("Show info", () -> {
            System.out.println(ship);
        }, false));

        menu.addOption(2, new Option("Show containers", () -> {
            System.out.println("----------------------");
            System.out.println("Containers on " + ship.getShipName());
            System.out.println("----------------------");

            if(ship.getContainers().size() > 0){
                for(Container container : ship.getContainers()){
                    System.out.println(container);
                    System.out.println("----------------------");
                }
            }
            else{
                System.out.println("Ship currently is full unloaded!");
            }
        }, false));

        menu.open();
    }

    public void loadShips() throws IOException {
        System.out.println("Loading ships...");
        File file = new File("ships.txt");

        if(!file.exists()){
            boolean created = file.createNewFile();
        }

        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            if(line.equalsIgnoreCase("---")){
                Ship ship = new Ship(
                        scanner.nextLine().split(" ")[1],
                        scanner.nextLine().split(" ")[1],
                        scanner.nextLine().split(" ")[1],
                        scanner.nextLine().split(" ")[1],
                        Integer.parseInt(scanner.nextLine().split(" ")[1]),
                        Double.parseDouble(scanner.nextLine().split(" ")[1]),
                        Integer.parseInt(scanner.nextLine().split(" ")[1]),
                        Integer.parseInt(scanner.nextLine().split(" ")[1]),
                        Integer.parseInt(scanner.nextLine().split(" ")[1])
                );

                ships.add(ship);
            }
            else if(line.equalsIgnoreCase("/")){

            }
            else if(line.equalsIgnoreCase("...")){
                break;
            }
        }
        System.out.println("Loaded " + ships.size() + " ships!");
    }

    public void saveShips() throws IOException {
        System.out.println("Saving ships...");
        File file = new File("ships.txt");

        if(file.exists()){
            file.delete();
            file.createNewFile();
        }

        PrintWriter printer = new PrintWriter(new FileWriter(file));

        Collections.sort(ships, new Comparator<Ship>() {
            @Override
            public int compare(Ship o1, Ship o2) {
                return o2.getShipName().compareTo(o1.getShipName());
            }
        });

        for(Ship ship : ships){
            printer.println("---");
            printer.println("ship: " + ship.getShipName());
            printer.println("port: " + ship.getPort());
            printer.println("from: " + ship.getFrom());
            printer.println("to: " + ship.getTo());
            printer.println("maxContainers: " + ship.getMaxContainers());
            printer.println("maxContainersMass: " + ship.getMaxContainersMass());
            printer.println("maxDangerousContainers: " + ship.getMaxDangerousContainers());
            printer.println("maxHeavyContainers: " + ship.getMaxHeavyContainers());
            printer.println("maxContainersWithElectricity: " + ship.getMaxContainersWithElectricity());
        }

        printer.println("...");
        printer.close();
        System.out.println("Saved ships!");
    }

    public void openShipCreator(){
        System.out.println("===================");
        System.out.println("Ship creator");
        System.out.println("===================");

        Scanner scanner = new Scanner(System.in);

        String shipName;
        String port;
        String from;
        String to;

        int maxContainers;
        double maxContainersMass;
        int maxDangerousContainers;
        int maxHeavyContainers;
        int maxContainersWithElectricity;

        System.out.print("Ship name: ");
        shipName = scanner.next();
        System.out.print("Creation port: ");
        port = scanner.next();
        System.out.print("From: ");
        from = scanner.next();
        System.out.print("To: ");
        to = scanner.next();

        System.out.print("Max containers: ");
        maxContainers = scanner.nextInt();
        System.out.print("Max mass of containers: ");
        maxContainersMass = scanner.nextDouble();
        System.out.print("Max dangerous containers: ");
        maxDangerousContainers = scanner.nextInt();
        System.out.print("Max heavy containers: ");
        maxHeavyContainers = scanner.nextInt();
        System.out.print("Max containers with electricity: ");
        maxContainersWithElectricity = scanner.nextInt();

        ships.add(new Ship(
                shipName,
                port,
                from,
                to,
                maxContainers,
                maxContainersMass,
                maxDangerousContainers,
                maxHeavyContainers,
                maxContainersWithElectricity
        ));

        System.out.println("Added new ship!");
    }

    public List<Ship> getShips() {
        return ships;
    }

    public Ship getShip(String shipName){
        for(Ship ship : ships){
            if(ship.getShipName().equalsIgnoreCase(shipName)){
                return ship;
            }
        }
        return null;
    }
}
