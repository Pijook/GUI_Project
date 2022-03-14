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
        File file = new File("ships.txt");

        if(!file.exists()){
            boolean created = file.createNewFile();
            if(created){
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                writer.print("---");
            }
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
    }

    public void saveShips(){

    }

    public List<Ship> getShips() {
        return ships;
    }
}
