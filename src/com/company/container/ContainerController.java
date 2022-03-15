package com.company.container;

import com.company.Main;
import com.company.container.exceptions.NotEnoughSpaceException;
import com.company.container.exceptions.TooManyDangerousContainersException;
import com.company.container.exceptions.TooManyElectricContainersException;
import com.company.container.exceptions.TooManyHeavyContainersException;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.ship.Ship;

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

    public void openContainersListMenu(){
        Menu menu = new Menu("Containers");
        int i = 1;
        for(Container container : containers.values()){
            menu.addOption(i, new Option(container.getContainerID().toString(), () -> {
                openContainerMenu(container);
            }, false));
            i++;
        }

        menu.open();
    }

    public void openContainerMenu(Container container){
        Menu menu = new Menu("Container " + container.getContainerID());

        menu.addOption(1, new Option("Show info about container", () -> {
            System.out.println(container);
        }, false));

        if(container.isLoadedOnShip()){
            menu.addOption(2, new Option("Unload from ship", () -> {
                Main.getShipController().getShip(container.getOnShip()).unLoadContainer(container);
            }, true));
        }
        else{
            menu.addOption(2, new Option("Load on ship", () -> {
                openLoadOnShipMenu(container);
            }, true));
        }

        menu.addOption(3, new Option("Remove container", () -> {

        }, true));

        menu.open();
    }

    public void openLoadOnShipMenu(Container container){
        Menu menu = new Menu("Choose ship");
        int i = 1;
        for(Ship ship : Main.getShipController().getShips()){
            menu.addOption(i, new Option(ship.getShipName(), () -> {
                try {
                    ship.loadContainer(container, false);
                    System.out.println("Loaded container on ship!");
                } catch (NotEnoughSpaceException e) {
                    System.out.println("Ship reached it's max capacity!");
                } catch (TooManyHeavyContainersException e) {
                    System.out.println("Ship reached max amount of heavy containers!");
                } catch (TooManyElectricContainersException e) {
                    System.out.println("Ship reached max amount of liquid containers!");
                } catch (TooManyDangerousContainersException e) {
                    System.out.println("Ship reached max amount of dangerous containers!");
                }
            }, true));
            i++;
        }

        menu.open();
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
        System.out.println("Container mass");
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
            containers.put(container.getContainerID(), container);
            System.out.print("Created new container!");
        }
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
     * @throws IOException Thrown when couldn't create or find file
     * @throws NotEnoughSpaceException Thrown when ship is overloaded
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

                        container = new HazardousHeavyContainer(mass, specialProtection, radiationLevel);
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

    /**
     * Loads shipped containers from shippedContainers.txt
     * @throws IOException Thrown when couldn't create or find file
     * @throws NotEnoughSpaceException Thrown when ship is overloaded
     * @throws TooManyHeavyContainersException Thrown when ship is overloaded with heavy containers
     * @throws TooManyDangerousContainersException Thrown when ship is overloaded with dangerous containers
     * @throws TooManyElectricContainersException Thrown when ship is overloaded with liquid containers
     */
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

    /**
     * Saves shipped containers in given order
     * @throws IOException Thrown when couldn't create or find file
     */
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

    /**
     * Saves all created containers
     * @throws IOException Thrown when couldn't create or find file
     */
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
