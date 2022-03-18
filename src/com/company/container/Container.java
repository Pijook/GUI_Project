package com.company.container;

import com.company.Main;
import com.company.container.exceptions.NotEnoughSpaceException;
import com.company.container.exceptions.TooManyDangerousContainersException;
import com.company.container.exceptions.TooManyElectricContainersException;
import com.company.container.exceptions.TooManyHeavyContainersException;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.ship.Ship;
import com.company.warehouse.exceptions.FullWarehouseException;

import java.util.UUID;

public class Container {

    private UUID containerID;
    private Double mass;
    private String onShip;
    private String senderID;

    public Container(double mass){
        this.containerID = UUID.randomUUID();
        this.mass = mass;
        this.onShip = null;
        this.senderID = null;
    }

    public void openContainerMenu(){
        Menu menu = new Menu("Container " + getContainerID());

        menu.addOption(1, new Option("Show info about container", () -> {
            System.out.println(this);
        }, false));

        if(!Main.getTrain().isOnTrain(this)){
            if(isLoadedOnShip()){
                menu.addOption(2, new Option("Unload from ship", () -> {
                    openUnloadMenu();
                }, true));
            }
            else{
                menu.addOption(2, new Option("Load on ship", () -> {
                    openLoadOnShipMenu();
                }, true));
            }

            menu.addOption(3, new Option("Remove container", () -> {
                if(isLoadedOnShip()){
                    System.out.println("Can't remove container that's stored on ship!");
                }
                else if(Main.getTrain().isOnTrain(this)){
                    System.out.println("Can't remove container that's stored on train!");
                }
                else{
                    Main.getWarehouse().removeContainer(this);
                }
            }, true));
        }

        menu.open();
    }

    public void openUnloadMenu(){
        Menu menu = new Menu("Choose unload destination");

        menu.addOption(1, new Option("Warehouse", () -> {
            try {
                Main.getShipController().getShip(getOnShip()).unLoadContainer(this, "warehouse");
            } catch (FullWarehouseException e) {
                System.out.println("Max warehouse capacity reached!");
            }
        }, true));

        menu.addOption(2, new Option("Train", () -> {
            if(!Main.getTrain().isOnTheWay()){
                try {
                    Main.getShipController().getShip(getOnShip()).unLoadContainer(this, "train");
                } catch (FullWarehouseException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Train already left station!");
            }
        }, true));
    }

    public void openLoadOnShipMenu(){
        Menu menu = new Menu("Choose ship");
        int i = 1;
        for(Ship ship : Main.getShipController().getShips()){
            menu.addOption(i, new Option(ship.getShipName(), () -> {
                try {
                    ship.loadContainer(this, false);
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

    public UUID getContainerID() {
        return containerID;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public void setContainerID(UUID containerID) {
        this.containerID = containerID;
    }

    public String getOnShip() {
        return onShip;
    }

    public void setOnShip(String onShip) {
        this.onShip = onShip;
    }

    public boolean isLoadedOnShip(){
        return this.onShip != null;
    }

    @Override
    public String toString() {
        return "type: Normal\n" +
                "containerID: " + containerID + "\n" +
                "mass: " + mass + "\n" +
                "onShip: " + onShip + "\n" +
                "senderID: " + senderID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
}
