package com.company.ship;

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
                ship.openShipMenu();
            }, false));
            i++;
        }

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

        System.out.print("Ship name: ");
        shipName = scanner.next();
        System.out.print("Creation port: ");
        port = scanner.next();
        System.out.print("From: ");
        from = scanner.next();
        System.out.print("To: ");
        to = scanner.next();

        int maxContainers = -1;
        double maxContainersMass = -1;
        int maxDangerousContainers = -1;
        int maxHeavyContainers = -1;
        int maxContainersWithElectricity = -1;

        while(maxContainers <= 0){
            try{
                System.out.print("Max containers: ");
                maxContainers = Integer.parseInt(scanner.next());
            }
            catch (NumberFormatException e){
                System.out.println("Enter valid number!");
            }
        }

        while(maxContainersMass <= 0){

            try{
                System.out.print("Max mass of containers: ");
                maxContainersMass = Double.parseDouble(scanner.next());
            }
            catch (NumberFormatException e){
                System.out.println("Enter valid number!");
            }
        }

        while(maxDangerousContainers < 0){
            try{
                System.out.print("Max dangerous containers: ");
                maxDangerousContainers = Integer.parseInt(scanner.next());
            }
            catch (NumberFormatException e){
                System.out.println("Enter valid number!");
            }
        }

        while(maxHeavyContainers < 0){
            try{
                System.out.print("Max heavy containers: ");
                maxHeavyContainers = Integer.parseInt(scanner.next());
            }
            catch (NumberFormatException e){
                System.out.println("Enter valid number!");
            }
        }

        while(maxContainersWithElectricity < 0){
            try{
                System.out.print("Max containers with electricity: ");
                maxContainersWithElectricity = Integer.parseInt(scanner.next());
            }
            catch (NumberFormatException e){
                System.out.println("Enter valid number!");
            }
        }

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
