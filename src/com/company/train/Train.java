package com.company.train;

import com.company.Main;
import com.company.container.Container;
import com.company.container.ExplodingContainer;
import com.company.container.containerTypes.Exploding;
import com.company.container.containerTypes.Hazardous;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.warehouse.StoredContainer;

import java.util.ArrayList;
import java.util.List;

public class Train extends Thread {

    private final List<StoredContainer> loadedContainers;
    private final int maxCapacity;
    private boolean onTheWay;

    public Train(int maxCapacity){
        this.loadedContainers = new ArrayList<>();
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void run() {
        try {
            System.out.println("Train left station!");

            //Show me the wae
            onTheWay = true;
            sleep(30 * 1000);
            for(StoredContainer storedContainer : loadedContainers){
                Container container = storedContainer.getContainer();
                if(container instanceof Hazardous || container instanceof Exploding){
                    Main.getSenderController().getSender(storedContainer.getContainer().getSenderID()).increaseWarnings(1);
                    try {
                        throw new IrresponsibleSenderWithDangerousGoods(storedContainer);
                    } catch (IrresponsibleSenderWithDangerousGoods e) {
                        e.printStackTrace();
                    }
                }
            }
            loadedContainers.clear();
            onTheWay = false;

            System.out.println("Train is back!");

        } catch (InterruptedException e) {
            this.stop();
            return;
        }

    }

    public void openTrainMenu(){
        Menu menu = new Menu("Train menu");

        menu.addOption(1, new Option("Show info", () -> {
            System.out.println("");
            System.out.println("Is on the way: " + onTheWay);
            System.out.println("Loaded containers: " + loadedContainers.size());
            System.out.println("Max capacity: " + maxCapacity);
            System.out.println("");
        }, false));

        menu.addOption(2, new Option("Manage containers", () -> {
            manageContainers();
        }, false));

        menu.open();
    }

    public void manageContainers(){
        Menu menu = new Menu("Manage train containers");

        int i = 1;
        for(StoredContainer storedContainer : loadedContainers){
            menu.addOption(i, new Option(storedContainer.getContainer().getContainerID().toString(), () -> {
                storedContainer.getContainer().openContainerMenu();
            }, false));
            i++;
        }
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
}
