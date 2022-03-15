package com.company.container;

import com.company.Main;
import com.company.container.exceptions.NotEnoughSpaceException;
import com.company.container.exceptions.TooManyDangerousContainersException;
import com.company.container.exceptions.TooManyElectricContainersException;
import com.company.container.exceptions.TooManyHeavyContainersException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ContainerController {

    private final HashMap<UUID, Container> containers;

    public ContainerController(){
        containers = new HashMap<>();
    }

    /**
     * Loads created containers from containers.txt files
     * Available containers types
     * - Normal
     * - Cooling
     * - Exploding
     * - Hazardous
     * - HazardousLiquid
     * - Heavy
     * - Liquid
     * @throws IOException
     * @throws NotEnoughSpaceException
     */
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

                double mass = Double.parseDouble(scanner.nextLine().split(" ")[1]);

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

    public void loadShippedContainers() throws
            IOException,
            NotEnoughSpaceException,
            TooManyHeavyContainersException,
            TooManyDangerousContainersException,
            TooManyElectricContainersException {

        File file = new File("shippedContainers.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()){
            String[] line = scanner.nextLine().split(" ");

            UUID containerID = UUID.fromString(line[0].replace(":", ""));
            String shipName = line[1];

            Container container = containers.get(containerID);
            container.setOnShip(shipName);

            Main.getShipController().getShip(shipName).loadContainer(container, true);
        }
    }

    public void saveShippedContainers() throws IOException {
        File file = new File("shippedContainers.txt");

        if(file.exists()){
            file.delete();
            file.createNewFile();
        }

        ArrayList<Container> containersToSave = new ArrayList<>();

        for(Container container : containers.values()){
            if(container.getOnShip() != null){
                containersToSave.add(container);
            }
        }

        Collections.sort(containersToSave, new Comparator<Container>() {
            @Override
            public int compare(Container o1, Container o2) {
                return o1.getMass().compareTo(o2.getMass());
            }
        });

        PrintWriter printer = new PrintWriter(new FileWriter(file));

        for(Container container : containersToSave){
            printer.println(container.getContainerID() + ": " + container.getOnShip());
        }

        printer.close();
    }

    public void saveContainers() throws IOException {
        System.out.println("Saving containers...");

        File file = new File("containers.txt");

        if(file.exists()){
            file.delete();
            file.createNewFile();
        }

        for(Container container : containers.values()){

        }

        System.out.println("Saved containers!");
    }

    public HashMap<UUID, Container> getContainers() {
        return containers;
    }
}
