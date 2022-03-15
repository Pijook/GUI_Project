package com.company.warehouse;

import com.company.Main;
import com.company.Train;
import com.company.container.*;
import com.company.warehouse.exceptions.FullWarehouseException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Warehouse extends Thread {

    private final List<StoredContainer> storedContainers;
    private final int maxContainers;

    public Warehouse(int maxContainers){
        this.maxContainers = maxContainers;
        this.storedContainers = new ArrayList<>();
    }

    @Override
    public void run() {
        LocalDate currentPortDate = Main.getPortTime().getPortDate();
        Iterator<StoredContainer> iterator = storedContainers.iterator();

        Train train = Main.getTrain();

        while(iterator.hasNext()){
            StoredContainer storedContainer = iterator.next();

            if(!train.isOnTheWay()){
                if(storedContainer.getContainer() instanceof HazardousHeavyContainer){
                    if(currentPortDate.compareTo(storedContainer.getStoreDate()) >= 10){
                        train.loadContainer(storedContainer.getContainer());
                        storedContainers.remove(storedContainer);
                    }
                }
                else if(storedContainer.getContainer() instanceof LiquidContainer){
                    if(currentPortDate.compareTo(storedContainer.getStoreDate()) >= 15){
                        train.loadContainer(storedContainer.getContainer());
                        storedContainers.remove(storedContainer);
                    }
                }
                else if(storedContainer.getContainer() instanceof ExplodingContainer){
                    if(currentPortDate.compareTo(storedContainer.getStoreDate()) >= 5){
                        train.loadContainer(storedContainer.getContainer());
                        storedContainers.remove(storedContainer);
                    }
                }
            }

        }
    }

    public void storeContainer(Container container) throws FullWarehouseException {
        if(storedContainers.size() >= maxContainers){
            throw new FullWarehouseException();
        }
        else{
            storedContainers.add(new StoredContainer(Main.getPortTime().getPortDate(), container));
        }
    }

    public List<StoredContainer> getStoredContainers() {
        return storedContainers;
    }

    public int getMaxContainers() {
        return maxContainers;
    }
}
