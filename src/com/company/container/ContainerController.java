package com.company.container;

import com.company.Main;
import com.company.container.exceptions.NotEnoughSpaceException;
import com.company.container.exceptions.TooManyDangerousContainersException;
import com.company.container.exceptions.TooManyElectricContainersException;
import com.company.container.exceptions.TooManyHeavyContainersException;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.ship.Ship;
import com.company.warehouse.StoredContainer;
import com.company.warehouse.exceptions.FullWarehouseException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ContainerController {

    public ContainerController(){

    }

    public void openCreateContainerMenu(){
        Menu menu = new Menu("Create container");

        menu.addOption(1, new Option("Normal container", () -> {
            openContainerEditor("normal");
        },true));

        menu.addOption(2, new Option("Cooling container", () -> {
            openContainerEditor("cooling");
        }, true));

        menu.addOption(3, new Option("Exploding container", () -> {
            openContainerEditor("exploding");
        }, true));

        menu.addOption(4, new Option("Hazardous container", () -> {
            openContainerEditor("hazardousheavy");
        }, true));

        menu.addOption(5, new Option("Liquid hazardous container", () -> {
            openContainerEditor("hazardousliquid");
        }, true));

        menu.addOption(6, new Option("Heavy container", () -> {
            openContainerEditor("heavy");
        }, true));

        menu.addOption(7, new Option("Liquid container", () -> {
            openContainerEditor("liquid");
        }, true));

        menu.open();
    }

    public void openContainerEditor(String containerType){
        Scanner scanner = new Scanner(System.in);

        System.out.println("===================");
        System.out.println("Container creator");
        System.out.println("===================");

        System.out.print("Container mass: ");
        double mass = scanner.nextDouble();

        Container container = null;
        switch (containerType){
            case "normal" -> {
                container = new Container(mass);
            }
            case "cooling" -> {
                String specialProtection;
                double minVoltage;

                System.out.print("Special protection: ");
                specialProtection = scanner.next();
                System.out.print("Minimum voltage: ");
                minVoltage = scanner.nextDouble();

                container = new CoolingContainer(mass, specialProtection, minVoltage);
            }
            case "exploding" -> {
                String specialProtection;
                double explosionRadius;

                System.out.print("Special protection: ");
                specialProtection = scanner.next();
                System.out.print("Explosion radius: ");
                explosionRadius = scanner.nextDouble();

                container = new ExplodingContainer(mass, specialProtection, explosionRadius);
            }
            case "hazardousheavy" -> {
                String specialProtection;
                double radiationLevel;

                System.out.print("Special protection: ");
                specialProtection = scanner.next();
                System.out.print("Radiation level: ");
                radiationLevel = scanner.nextDouble();
                container = new HazardousHeavyContainer(mass, specialProtection, radiationLevel);
            }
            case "hazardousliquid" -> {
                double maxCapacity;
                double radiationLevel;

                System.out.print("Max capacity (in percent): ");
                maxCapacity = scanner.nextDouble();
                System.out.print("Radiation level: ");
                radiationLevel = scanner.nextDouble();

                container = new HazardousLiquidContainer(mass,maxCapacity, radiationLevel);
            }
            case "heavy" -> {
                String specialProtection;

                System.out.print("Special protection: ");
                specialProtection = scanner.next();

                container = new HeavyContainer(mass, specialProtection);
            }
            case "liquid" -> {
                double maxCapacity;

                System.out.print("Max capacity (in percent): ");
                maxCapacity = scanner.nextDouble();

                container = new LiquidContainer(mass, maxCapacity);
            }
        }

        if(container != null){
            try {
                Main.getWarehouse().storeContainer(container);
                //containers.put(container.getContainerID(), container);
            } catch (FullWarehouseException e) {
                e.printStackTrace();
                System.out.println("Warehouse max capacity reached!");
            }
            System.out.println("Created new container!");
        }
    }

    /**
     * Loads created containers from shippedContainers.txt files
     * Available containers types
     * - Normal
     * - Cooling
     * - Exploding
     * - Hazardous
     * - HazardousLiquid
     * - Heavy
     * - Liquid
     * @throws IOException Thrown when couldn't create or find file
     * @throws NotEnoughSpaceException Thrown when ship is overloaded
     */
    public void loadContainers() throws IOException {
        System.out.println("Loading containers...");

        int shipped = loadShippedContainers();
        int stored = loadStoredContainers();

        System.out.println("Loaded " + shipped + " shipped containers!");
        System.out.println("Loaded " + stored + " stored containers!");
    }

    private int loadShippedContainers() throws IOException {
        File file = new File("shippedContainers.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int amount = 0;

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            if(line.equalsIgnoreCase("/")){
                Container container = null;

                String text = "";
                int i = 0;
                while(!text.equalsIgnoreCase("/") && scanner.hasNext()){
                    if(i > 0){
                        text += ";";
                    }
                    text += scanner.nextLine();
                    i++;
                }

                container = stringToContainer(text.split(";"));

                amount++;
                if(container.isLoadedOnShip()){
                    try {
                        Main.getShipController().getShip(container.getOnShip()).loadContainer(container, true);
                    } catch (NotEnoughSpaceException e) {
                        e.printStackTrace();
                    } catch (TooManyHeavyContainersException e) {
                        e.printStackTrace();
                    } catch (TooManyElectricContainersException e) {
                        e.printStackTrace();
                    } catch (TooManyDangerousContainersException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return amount;
    }

    private int loadStoredContainers() throws IOException {
        File file = new File("storedContainers.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int amount = 0;
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            if(line.equalsIgnoreCase("/")){
                StoredContainer storedContainer;

                String text = "";
                int i = 0;
                while(!text.equalsIgnoreCase("/") && scanner.hasNext()){
                    if(i > 0){
                        text += ";";
                    }
                    text += scanner.nextLine();
                    i++;
                }

                storedContainer = stringToStoredContainer(text.split(";"));
                Main.getWarehouse().forceAddContainer(storedContainer);
                amount++;
            }
        }

        return amount;
    }

    /**
     * Saves all created containers
     * @throws IOException Thrown when couldn't create or find file
     */
    public void saveShippedContainers() throws IOException {
        System.out.println("Saving shipped containers...");

        File file = new File("shippedContainers.txt");

        if(file.exists()){
            file.delete();
            file.createNewFile();
        }

        List<Container> containerList = new ArrayList<>();

        for(Ship ship : Main.getShipController().getShips()){
            containerList.addAll(ship.getContainers());
        }

        containerList.sort(new Comparator<Container>() {
            @Override
            public int compare(Container o1, Container o2) {
                return o2.getOnShip().compareTo(o1.getOnShip());
            }
        });

        PrintWriter printer = new PrintWriter(new FileWriter(file));

        for(Container container : containerList){
            printer.println("/");
            printer.println(container.toString());
        }

        printer.close();

        System.out.println("Saved shipped containers!");
    }

    public void saveStoredContainers() throws IOException {
        System.out.println("Saving stored containers...");

        File file = new File("storedContainers.txt");

        if(file.exists()){
            file.delete();
            file.createNewFile();
        }

        List<StoredContainer> storedContainers = new ArrayList<>(Main.getWarehouse().getStoredContainers());


        storedContainers.sort(new Comparator<StoredContainer>() {
            @Override
            public int compare(StoredContainer o1, StoredContainer o2) {
                return o1.getStoreDate().compareTo(o2.getStoreDate());
            }
        });

        PrintWriter printer = new PrintWriter(new FileWriter(file));

        for(StoredContainer storedContainer : storedContainers){
            printer.println("/");
            printer.println(storedContainer.toString());
        }

        printer.close();

        System.out.println("Saved stored containers!");
    }

    private Container stringToContainer(String[] lines){
        String containerType = lines[0].split(" ")[1];
        UUID containerID = UUID.fromString(lines[1].split(" ")[1]);
        double mass = Double.parseDouble(lines[2].split(" ")[1]);

        Container container = null;
        switch (containerType){
            case "Normal" -> {
                //Just normal container
                //Stay chill and be cool B)
                container = new Container(mass);
            }
            case "Cooling" -> {
                int minVoltage = Integer.parseInt(lines[3].split(" ")[1]);
                String specialProtection = lines[4].split(" ")[1];

                container = new CoolingContainer(mass, specialProtection, minVoltage);
            }
            case "Exploding" -> {
                double explosionRadius = Double.parseDouble(lines[3].split(" ")[1]);
                String specialProtection = lines[4].split(" ")[1];

                container = new ExplodingContainer(mass, specialProtection, explosionRadius);
            }
            case "HeavyHazardous" -> {
                String specialProtection = lines[3].split(" ")[1];
                double radiationLevel = Double.parseDouble(lines[4].split(" ")[1]);

                container = new HazardousHeavyContainer(mass, specialProtection, radiationLevel);
            }
            case "HazardousLiquid" -> {
                double maxCapacity = Double.parseDouble(lines[3].split(" ")[1]);
                double radiationLevel = Double.parseDouble(lines[4].split(" ")[1]);

                container = new HazardousLiquidContainer(mass, maxCapacity,radiationLevel);
            }
            case "Heavy" -> {
                String specialProtection = lines[3].split(" ")[1];

                container = new HeavyContainer(mass, specialProtection);
            }
            case "Liquid" -> {
                double maxCapacity = Double.parseDouble(lines[3].split(" ")[1]);

                container = new LiquidContainer(mass, maxCapacity);
            }
        }

        String shipName = lines[lines.length - 1].split(" ")[1];
        if(!shipName.equalsIgnoreCase("null")){
            container.setOnShip(lines[lines.length - 1].split(" ")[1]);
        }
        container.setContainerID(containerID);
        return container;
    }

    private StoredContainer stringToStoredContainer(String[] lines){
        String containerType = lines[0].split(" ")[1];
        UUID containerID = UUID.fromString(lines[1].split(" ")[1]);
        double mass = Double.parseDouble(lines[2].split(" ")[1]);

        Container container = null;
        switch (containerType){
            case "Normal" -> {
                //Just normal container
                //Stay chill and be cool B)
                container = new Container(mass);
            }
            case "Cooling" -> {
                int minVoltage = Integer.parseInt(lines[3].split(" ")[1]);
                String specialProtection = lines[4].split(" ")[1];

                container = new CoolingContainer(mass, specialProtection, minVoltage);
            }
            case "Exploding" -> {
                double explosionRadius = Double.parseDouble(lines[3].split(" ")[1]);
                String specialProtection = lines[4].split(" ")[1];

                container = new ExplodingContainer(mass, specialProtection, explosionRadius);
            }
            case "HeavyHazardous" -> {
                String specialProtection = lines[3].split(" ")[1];
                double radiationLevel = Double.parseDouble(lines[4].split(" ")[1]);

                container = new HazardousHeavyContainer(mass, specialProtection, radiationLevel);
            }
            case "HazardousLiquid" -> {
                double maxCapacity = Double.parseDouble(lines[3].split(" ")[1]);
                double radiationLevel = Double.parseDouble(lines[4].split(" ")[1]);

                container = new HazardousLiquidContainer(mass, maxCapacity,radiationLevel);
            }
            case "Heavy" -> {
                String specialProtection = lines[3].split(" ")[1];

                container = new HeavyContainer(mass, specialProtection);
            }
            case "Liquid" -> {
                double maxCapacity = Double.parseDouble(lines[3].split(" ")[1]);

                container = new LiquidContainer(mass, maxCapacity);
            }
        }
        String lastLine = lines[lines.length - 1];
        LocalDate date = LocalDate.parse(lastLine.split(" ")[1]);
        container.setContainerID(containerID);

        return new StoredContainer(date, container);
    }
}
