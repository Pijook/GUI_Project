package com.company.container;

import com.company.Main;
import com.company.container.exceptions.NotEnoughSpaceException;
import com.company.container.exceptions.TooManyDangerousContainersException;
import com.company.container.exceptions.TooManyElectricContainersException;
import com.company.container.exceptions.TooManyHeavyContainersException;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.sender.Sender;
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
        Menu menu = new Menu("Choose sender");

        List<Sender> senders = Main.getSenderController().getSenders();

        if(senders.size() == 0){
            System.out.println("");
            System.out.println("You must create sender before creating container!");
            System.out.println("");
            return;
        }

        int i = 1;
        for(Sender sender : senders){
            menu.addOption(i, new Option(sender.getName() + " " + sender.getSurname(), () -> {
                openSelectCrateCategoryMenu(sender.getUserID());
            }, true));
            i++;
        }

        menu.open();
    }

    public void openSelectCrateCategoryMenu(String senderID){
        Menu menu = new Menu("Create container");

        menu.addOption(1, new Option("Normal container", () -> {
            openContainerEditor("normal", senderID);
        },true));

        menu.addOption(2, new Option("Cooling container", () -> {
            openContainerEditor("cooling", senderID);
        }, true));

        menu.addOption(3, new Option("Exploding container", () -> {
            openContainerEditor("exploding", senderID);
        }, true));

        menu.addOption(4, new Option("Hazardous container", () -> {
            openContainerEditor("hazardousheavy", senderID);
        }, true));

        menu.addOption(5, new Option("Liquid hazardous container", () -> {
            openContainerEditor("hazardousliquid", senderID);
        }, true));

        menu.addOption(6, new Option("Heavy container", () -> {
            openContainerEditor("heavy", senderID);
        }, true));

        menu.addOption(7, new Option("Liquid container", () -> {
            openContainerEditor("liquid", senderID);
        }, true));

        menu.open();
    }

    public void openContainerEditor(String containerType, String senderID){
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        System.out.println("===================");
        System.out.println("Container creator");
        System.out.println("===================");

        System.out.print("Container mass: ");
        double mass = 0;
        while(mass <= 0){
            try{
                mass = Double.parseDouble(scanner.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Insert valid mass!");
                mass = -1;
            }
        }

        Container container = null;
        switch (containerType){
            case "normal" -> {
                container = new NormalContainer(mass);
            }
            case "cooling" -> {
                String specialProtection;
                double minVoltage = 0;

                System.out.print("Special protection: ");
                specialProtection = scanner.nextLine();

                while(minVoltage <= 0){
                    try{
                        System.out.print("Minimum voltage: ");
                        minVoltage = Double.parseDouble(scanner.nextLine());
                    }
                    catch (NumberFormatException e){
                        System.out.println("Invalid voltage!");
                        minVoltage = -1;
                    }
                }

                container = new CoolingContainer(mass, specialProtection, minVoltage);
            }
            case "exploding" -> {
                String specialProtection;
                double explosionRadius = 0;

                System.out.print("Special protection: ");
                specialProtection = scanner.nextLine();

                while(explosionRadius <= 0){
                    try{
                        System.out.print("Explosion radius: ");
                        explosionRadius = Double.parseDouble(scanner.nextLine());
                    }
                    catch (NumberFormatException e){
                        System.out.println("Enter valid radius!");
                        explosionRadius = 0;
                    }
                }

                container = new ExplodingContainer(mass, specialProtection, explosionRadius);
            }
            case "hazardousheavy" -> {
                String specialProtection;
                double radiationLevel = 0;

                System.out.print("Special protection: ");
                specialProtection = scanner.nextLine();

                while(radiationLevel <= 0){
                    try{
                        System.out.print("Radiation level: ");
                        radiationLevel = Double.parseDouble(scanner.nextLine());
                    }
                    catch (NumberFormatException e){
                        System.out.println("Enter valid radiation level!");
                        radiationLevel = 0;
                    }
                }

                container = new HazardousHeavyContainer(mass, specialProtection, radiationLevel);
            }
            case "hazardousliquid" -> {
                double maxCapacity = 0;
                double radiationLevel = 0;
                String specialProtection;

                System.out.print("Special protection: ");
                specialProtection = scanner.nextLine();

                while(maxCapacity <= 0){
                    try{
                        System.out.print("Max capacity (in percent): ");
                        maxCapacity = Double.parseDouble(scanner.nextLine());
                    }
                    catch (NumberFormatException e){
                        System.out.println("Enter valid number!");
                        maxCapacity = 0;
                    }

                }

                while(radiationLevel <= 0){
                    try{
                        System.out.print("Radiation level: ");
                        radiationLevel = Double.parseDouble(scanner.nextLine());
                    }
                    catch (NumberFormatException e){
                        System.out.println("Enter valid radiation level!");
                        radiationLevel = 0;
                    }
                }

                container = new HazardousLiquidContainer(mass, specialProtection,maxCapacity, radiationLevel);
            }
            case "heavy" -> {
                String specialProtection;

                System.out.print("Special protection: ");
                specialProtection = scanner.nextLine();

                container = new HeavyContainer(mass, specialProtection);
            }
            case "liquid" -> {
                double maxCapacity = 0;

                while(maxCapacity <= 0){
                    try{
                        System.out.print("Max capacity (in percent): ");
                        maxCapacity = Double.parseDouble(scanner.nextLine());
                    }
                    catch (NumberFormatException e){
                        System.out.println("Enter valid number!");
                        maxCapacity = 0;
                    }

                }

                container = new LiquidContainer(mass, maxCapacity);
            }
        }

        if(container != null){
            try {
                container.setSenderID(senderID);
                Main.getWarehouse().storeContainer(container);
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
                if(o1.getStoreDate().compareTo(o2.getStoreDate()) == 0){
                    Sender o1Sender = Main.getSenderController().getSender(o1.getContainer().getSenderID());
                    Sender o2Sender = Main.getSenderController().getSender(o2.getContainer().getSenderID());

                    return o1Sender.getName().compareTo(o2Sender.getName());
                }
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

    /*public Container stringToContainer(String[] lines){
        String containerType = lines[0].split(" ")[1];
        UUID containerID = UUID.fromString(lines[1].split(" ")[1]);
        double mass = Double.parseDouble(lines[2].split(" ")[1]);

        Container container = null;
        switch (containerType){
            case "Normal" -> {
                //Just normal container
                //Stay chill and drink lemonade
                container = new Container(mass);
            }
            case "Cooling" -> {
                double minVoltage = Double.parseDouble(lines[3].split(" ")[1]);
                String specialProtection = getSpecialProtection(lines[4]);

                container = new CoolingContainer(mass, specialProtection, minVoltage);
            }
            case "Exploding" -> {
                double explosionRadius = Double.parseDouble(lines[3].split(" ")[1]);
                String specialProtection = getSpecialProtection(lines[4]);

                container = new ExplodingContainer(mass, specialProtection, explosionRadius);
            }
            case "HeavyHazardous" -> {
                String specialProtection = getSpecialProtection(lines[3]);
                double radiationLevel = Double.parseDouble(lines[4].split(" ")[1]);

                container = new HazardousHeavyContainer(mass, specialProtection, radiationLevel);
            }
            case "HazardousLiquid" -> {
                double maxCapacity = Double.parseDouble(lines[3].split(" ")[1]);
                String specialProtection = getSpecialProtection(lines[4]);
                double radiationLevel = Double.parseDouble(lines[5].split(" ")[1]);

                container = new HazardousLiquidContainer(mass, specialProtection, maxCapacity,radiationLevel);
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

        String senderID = lines[lines.length - 2].split(" ")[1];
        String shipName = lines[lines.length - 1].split(" ")[1];
        if(!shipName.equalsIgnoreCase("null")){
            container.setOnShip(shipName);
        }
        container.setContainerID(containerID);
        container.setSenderID(senderID);
        return container;
    }*/

    public Container stringToContainer(String[] lines){
        String containerType = lines[0].split(" ")[1];

        UUID containerID = UUID.fromString(lines[lines.length - 4].split(" ")[1]);
        double mass = Double.parseDouble(lines[lines.length - 3].split(" ")[1]);
        String senderID = lines[lines.length - 2];
        String shipName = getMultiWordValue(lines[lines.length - 1]);

        Container container = null;
        switch (containerType){
            case "Normal" -> {
                //Just normal container
                //Stay chill and drink lemonade
                container = new NormalContainer(mass);
            }
            case "Cooling" -> {
                double minVoltage = Double.parseDouble(lines[1].split(" ")[1]);
                String specialProtection = getMultiWordValue(lines[2]);

                container = new CoolingContainer(mass, specialProtection, minVoltage);
            }
            case "Exploding" -> {
                double explosionRadius = Double.parseDouble(lines[1].split(" ")[1]);
                String specialProtection = getMultiWordValue(lines[2]);

                container = new ExplodingContainer(mass, specialProtection, explosionRadius);
            }
            case "HeavyHazardous" -> {
                String specialProtection = getMultiWordValue(lines[2]);
                double radiationLevel = Double.parseDouble(lines[1].split(" ")[1]);

                container = new HazardousHeavyContainer(mass, specialProtection, radiationLevel);
            }
            case "HazardousLiquid" -> {
                double maxCapacity = Double.parseDouble(lines[2].split(" ")[1]);
                String specialProtection = getMultiWordValue(lines[3]);
                double radiationLevel = Double.parseDouble(lines[1].split(" ")[1]);

                container = new HazardousLiquidContainer(mass, specialProtection, maxCapacity,radiationLevel);
            }
            case "Heavy" -> {
                String specialProtection = getMultiWordValue(lines[1]);

                container = new HeavyContainer(mass, specialProtection);
            }
            case "Liquid" -> {
                double maxCapacity = Double.parseDouble(lines[1].split(" ")[1]);

                container = new LiquidContainer(mass, maxCapacity);
            }
        }

        if(!shipName.equalsIgnoreCase("null")){
            container.setOnShip(shipName);
        }
        container.setContainerID(containerID);
        container.setSenderID(senderID);
        return container;
    }

    private String getMultiWordValue(String line){
        String[] lines = line.split(" ");
        StringBuilder a = new StringBuilder();
        for(int i = 1; i < lines.length; i++){
            if(i > 1){
                a.append(" ");
            }
            a.append(lines[i]);
        }
        return a.toString();
    }

    public StoredContainer stringToStoredContainer(String[] lines){
        String[] containerLines = new String[lines.length - 1];

        for(int i = 0; i < lines.length - 1; i++){
            containerLines[i] = lines[i];
        }

        Container container = stringToContainer(containerLines);
        LocalDate date = LocalDate.parse(lines[lines.length - 1].split(" ")[1]);
        return new StoredContainer(date, container);
    }
}
