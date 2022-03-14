package com.company.container;

import com.company.Main;
import com.company.container.exceptions.NotEnoughSpaceException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class ContainerController {

    private final HashMap<UUID, Container> containers;

    public ContainerController(){
        containers = new HashMap<>();
    }

    public void loadContainers() throws IOException, NotEnoughSpaceException {
        System.out.println("Loading containers...");

        File file = new File("containers.txt");

        if(!file.exists()){
            boolean created = file.createNewFile();
        }

        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            if(line.equalsIgnoreCase("/")){
                Container container = null;

                UUID containerID = UUID.fromString(scanner.nextLine().split(" ")[1]);

                String lineMassOrShip = scanner.nextLine();
                double mass;
                if(lineMassOrShip.contains("loadedOn")){
                    Main.getShipController().getShip(lineMassOrShip.split(" ")[1]).loadContainer(container, true);
                    mass = Double.parseDouble(scanner.nextLine().split(" ")[1]);
                }
                else{
                    mass = Double.parseDouble(lineMassOrShip.split(" ")[1]);
                }

                String type = scanner.nextLine().split(" ")[1];

                switch (type){
                    case "Normal" -> {
                        //Just normal container
                        //Stay chill and be cool B)
                        container = new Container(mass);
                    }
                    case "Cooling" -> {
                        int minVoltage = Integer.parseInt(scanner.nextLine().split(" ")[1]);
                        String specialProtection = scanner.nextLine().split(" ")[1];

                        container = new CoolingContainer(mass, specialProtection, minVoltage);
                    }
                    case "Exploding" -> {
                        double explosionRadius = Double.parseDouble(scanner.nextLine().split(" ")[1]);
                        String specialProtection = scanner.nextLine().split(" ")[1];

                        container = new ExplodingContainer(mass, specialProtection, explosionRadius);
                    }
                    case "Hazardous" -> {
                        double radiationLevel = Double.parseDouble(scanner.nextLine().split(" ")[1]);
                        String specialProtection = scanner.nextLine().split(" ")[1];

                        container = new HazardousContainer(mass, specialProtection, radiationLevel);
                    }
                    case "HazardousLiquid" -> {
                        double radiationLevel = Double.parseDouble(scanner.nextLine().split(" ")[1]);
                        double maxCapacity = Double.parseDouble(scanner.nextLine().split(" ")[1]);

                        container = new HazardousLiquidContainer(mass, maxCapacity,radiationLevel);
                    }
                    case "Heavy" -> {
                        String specialProtection = scanner.nextLine().split(" ")[1];

                        container = new HeavyContainer(mass, specialProtection);
                    }
                    case "Liquid" -> {
                        double maxCapacity = Double.parseDouble(scanner.nextLine().split(" ")[1]);

                        container = new LiquidContainer(mass, maxCapacity);
                    }
                }

                if(container != null){
                    container.setContainerID(containerID);
                }

                containers.put(containerID, container);
            }
        }

        System.out.println("Loaded " + containers.size() + " containers!");
    }


    public void saveContainers(){
        System.out.println("Saving containers...");
        System.out.println("Saved containers!");
    }

    public HashMap<UUID, Container> getContainers() {
        return containers;
    }
}
