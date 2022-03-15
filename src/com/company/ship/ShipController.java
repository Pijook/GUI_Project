package com.company.ship;

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
