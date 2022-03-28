package com.company.train;

import com.company.Main;
import com.company.container.Container;
import com.company.container.containerTypes.Exploding;
import com.company.container.containerTypes.Hazardous;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.sender.Sender;
import com.company.sender.Warning;
import com.company.warehouse.StoredContainer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Train extends Thread {

    private final List<StoredContainer> loadedContainers;
    private int maxCapacity;
    private boolean onTheWay;
    private int trainTime;

    public Train(int maxCapacity){
        this.loadedContainers = new ArrayList<>();
        this.maxCapacity = maxCapacity;
        this.onTheWay = false;
        this.trainTime = -1;
    }

    public void load() throws IOException {
        File file = new File("train.txt");

        if(!file.exists()){
            file.createNewFile();
            return;
        }

        Scanner scanner = new Scanner(file);

        maxCapacity = Integer.parseInt(scanner.nextLine().split(" ")[1]);
        onTheWay = Boolean.parseBoolean(scanner.nextLine().split(" ")[1]);
        trainTime = Integer.parseInt(scanner.nextLine().split(" ")[1]);

        while(scanner.hasNext()){
            String line = scanner.nextLine();
            if(line.equalsIgnoreCase("/")){
                StoredContainer storedContainer;

                String text = Main.getContainerController().getTextBetweenSymbols(scanner, "/");

                storedContainer = Main.getContainerController().stringToStoredContainer(text.split(";"));
                loadedContainers.add(storedContainer);
            }
        }

        if(onTheWay){
            this.start();
        }
    }

    public void save() throws IOException {
        File file = new File("train.txt");

        if(file.exists()){
            file.delete();
            file.createNewFile();
        }

        PrintWriter printer = new PrintWriter(new FileWriter(file));

        printer.println("maxCapacity: " + maxCapacity);
        printer.println("onTheWay: " + onTheWay);
        printer.println("trainTime: " + trainTime);

        for(StoredContainer storedContainer : loadedContainers){
            printer.println("/");
            printer.println(storedContainer.toString());
            printer.println("/");
        }

        printer.close();
    }

    @Override
    public void run() {
        try {
            System.out.println("\nTrain left station!\n");

            if(trainTime == -1){
                trainTime = 30;
            }

            onTheWay = true;

            //Show me the wae
            while(!isInterrupted()){
                sleep(1000);
                trainTime--;

                if(trainTime <= 0){
                    break;
                }
            }

            for(StoredContainer storedContainer : loadedContainers){
                Container container = storedContainer.getContainer();
                if(container instanceof Hazardous || container instanceof Exploding){
                    Main.getSenderController().getSender(storedContainer.getContainer().getSenderID())
                            .addWarning(new Warning(
                                    Main.getPortTime().getPortDate(),
                                    storedContainer
                            ));
                    try {
                        throw new IrresponsibleSenderWithDangerousGoods(storedContainer);
                    } catch (IrresponsibleSenderWithDangerousGoods e) {
                        Sender sender = Main.getSenderController().getSender(container.getSenderID());
                        System.out.println("\n" + sender.getName() + " got warning for leaving dangerous container with ID " + container.getContainerID() + " in warehouse for too long!\n");
                    }
                }
            }

            loadedContainers.clear();
            onTheWay = false;
            trainTime = -1;

            System.out.println("\nTrain is back!\n");

        } catch (InterruptedException e) {
            return;
        }

    }

    public Menu getTrainMenu(){
        Menu menu = new Menu("Train menu");

        menu.addOption(1, new Option("Show info", () -> {
            System.out.println("");
            System.out.println("Is on the way: " + onTheWay);
            System.out.println("Loaded containers: " + loadedContainers.size());
            System.out.println("Max capacity: " + maxCapacity);
            System.out.println("");
        }, false));

        menu.addOption(2, new Option("Manage containers", () -> {
            menu.goToMenu(getManageContainersMenu());
        }, false));

        return menu;
    }

    public Menu getManageContainersMenu(){
        Menu menu = new Menu("Manage train containers");

        int i = 1;
        for(StoredContainer storedContainer : loadedContainers){
            menu.addOption(i, new Option(storedContainer.getContainer().getContainerID().toString(), () -> {
                menu.goToMenu(storedContainer.getContainer().getContainerMenu());
            }, false));
            i++;
        }

        return menu;
    }

    public boolean isOnTrain(Container container){
        for(StoredContainer storedContainer : loadedContainers){
            if(storedContainer.getContainer().equals(container)){
                return true;
            }
        }
        return false;
    }

    public void loadContainer(StoredContainer storedContainer){
        loadedContainers.add(storedContainer);
        if(loadedContainers.size() >= maxCapacity){
            this.start();
        }
    }

    public boolean isOnTheWay() {
        return onTheWay;
    }

    public void setOnTheWay(boolean onTheWay) {
        this.onTheWay = onTheWay;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public List<StoredContainer> getLoadedContainers() {
        return loadedContainers;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(int trainTime) {
        this.trainTime = trainTime;
    }
}
