package com.company.ship;

import com.company.Main;
import com.company.container.*;
import com.company.container.containerTypes.Hazardous;
import com.company.container.exceptions.NotEnoughSpaceException;
import com.company.container.exceptions.TooManyElectricContainersException;
import com.company.container.exceptions.TooManyDangerousContainersException;
import com.company.container.exceptions.TooManyHeavyContainersException;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.sender.Sender;
import com.company.warehouse.StoredContainer;
import com.company.warehouse.exceptions.FullWarehouseException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ship {

    private String shipName;
    private String port;
    private String from;
    private String to;

    private Integer maxContainers;
    private Double maxContainersMass;
    private Integer maxDangerousContainers;
    private Integer maxHeavyContainers;
    private Integer maxContainersWithElectricity;

    private List<Container> containers;

    public Ship(String shipName, String port, String from, String to, Integer maxContainers, Double maxContainersMass, Integer maxDangerousContainers, Integer maxHeavyContainers, Integer maxContainersWithElectricity) {
        this.shipName = shipName;
        this.port = port;
        this.from = from;
        this.to = to;
        this.maxContainers = maxContainers;
        this.maxContainersMass = maxContainersMass;
        this.maxDangerousContainers = maxDangerousContainers;
        this.maxHeavyContainers = maxHeavyContainers;
        this.maxContainersWithElectricity = maxContainersWithElectricity;

        this.containers = new ArrayList<>();
    }

    public void openShipMenu(){
        Menu menu = new Menu("Ship " + getShipName());

        menu.addOption(1, new Option("Show info", () -> {
            System.out.println(this);
        }, false));

        menu.addOption(2, new Option("Show containers", () -> {
            System.out.println("----------------------");
            System.out.println("Containers on " + getShipName());
            System.out.println("----------------------");

            if(getContainers().size() > 0){
                for(Container container : getContainers()){
                    System.out.println(container);
                    System.out.println("----------------------");
                }
            }
            else{
                System.out.println("Ship is currently empty!");
            }
        }, false));

        menu.addOption(3, new Option("Manage containers", () -> {
            openManageContainersOnShipMenu();
        }, false));

        menu.addOption(4, new Option("Leave port", () -> {
            System.out.println("");
            System.out.println(" " + getShipName() + " left port forever");
            System.out.println("");

            Main.getShipController().getShips().remove(this);
        }, true));

        menu.open();
    }

    private void openManageContainersOnShipMenu(){
        Menu menu = new Menu("Manage containers");

        int i = 1;
        for(Container container : getContainers()){
            menu.addOption(i, new Option(container.getContainerID().toString(), new Runnable() {
                @Override
                public void run() {
                    container.openContainerMenu();
                }
            }, false));
            i++;
        }

        menu.open();
    }

    public void loadContainer(Container container, boolean force) throws NotEnoughSpaceException, TooManyHeavyContainersException, TooManyElectricContainersException, TooManyDangerousContainersException {
        if (!force) {
            double loadedMass = 0.0;
            int dangerousContainers = 0;
            int electric = 0;
            int heavy = 0;
            for (Container temp : containers) {
                loadedMass += temp.getMass();

                if (temp instanceof Hazardous || temp instanceof ExplodingContainer) {
                    dangerousContainers++;
                } else if (temp instanceof CoolingContainer) {
                    electric++;
                } else if (temp instanceof HeavyContainer) {
                    heavy++;
                }
            }

            if (container.getMass() + loadedMass > maxContainersMass || containers.size() + 1 > maxContainers) {
                throw new NotEnoughSpaceException();
            } else if (dangerousContainers > maxDangerousContainers) {
                throw new TooManyDangerousContainersException();
            } else if (electric > maxContainersWithElectricity) {
                throw new TooManyElectricContainersException();
            } else if (heavy > maxHeavyContainers) {
                throw new TooManyHeavyContainersException();
            }

        }
        containers.add(container);
        container.setOnShip(shipName);
        Main.getWarehouse().removeContainer(container);
    }

    public void unLoadContainer(Container container, String destination) throws FullWarehouseException {
        Sender sender = Main.getSenderController().getSender(container.getSenderID());
        if(sender.getWarnings() >= 2){
            System.out.println("");
            System.out.println(sender.getName() + " " + sender.getSurname() + " is not welcome in this port!");
            System.out.println("");
            return;
        }

        containers.remove(container);
        container.setOnShip(null);

        if(destination.equalsIgnoreCase("warehouse")){
            Main.getWarehouse().storeContainer(container);
        }
        else if(destination.equalsIgnoreCase("train")){
            Main.getTrain().loadContainer(new StoredContainer(LocalDate.now(), container));
        }
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getMaxContainers() {
        return maxContainers;
    }

    public void setMaxContainers(Integer maxContainers) {
        this.maxContainers = maxContainers;
    }

    public Double getMaxContainersMass() {
        return maxContainersMass;
    }

    public void setMaxContainersMass(Double maxContainersMass) {
        this.maxContainersMass = maxContainersMass;
    }

    public Integer getMaxDangerousContainers() {
        return maxDangerousContainers;
    }

    public void setMaxDangerousContainers(Integer maxDangerousContainers) {
        this.maxDangerousContainers = maxDangerousContainers;
    }

    public Integer getMaxHeavyContainers() {
        return maxHeavyContainers;
    }

    public void setMaxHeavyContainers(Integer maxHeavyContainers) {
        this.maxHeavyContainers = maxHeavyContainers;
    }

    public Integer getMaxContainersWithElectricity() {
        return maxContainersWithElectricity;
    }

    public void setMaxContainersWithElectricity(Integer maxContainersWithElectricity) {
        this.maxContainersWithElectricity = maxContainersWithElectricity;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    @Override
    public String toString() {
        return "Ship " +
                "Name: " + shipName + "\n" +
                "Port: " + port + "\n" +
                "From: " + from + "\n" +
                "To: " + to + "\n" +
                "Max Containers: " + maxContainers + "\n" +
                "Max containers mass: " + maxContainersMass + "\n" +
                "Max dangerous containers: " + maxDangerousContainers + "\n" +
                "Max heavy containers: " + maxHeavyContainers + "\n" +
                "Max containers with electricity: " + maxContainersWithElectricity;
    }
}
