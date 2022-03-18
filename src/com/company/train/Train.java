package com.company.train;

import com.company.Main;
import com.company.container.Container;
import com.company.container.ExplodingContainer;
import com.company.container.containerTypes.Exploding;
import com.company.container.containerTypes.Hazardous;
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
            return;
        }

    }

    public boolean isOnTrain(Container container){
        return loadedContainers.contains(container);
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
