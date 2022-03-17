package com.company.warehouse;

import com.company.Main;
import com.company.menu.Menu;
import com.company.menu.Option;
import com.company.train.Train;
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

    public void openWarehouseMenu(){
        Menu menu = new Menu("Warehouse menu");

        menu.addOption(1, new Option("Show stored containers", () -> {
            for(StoredContainer container : storedContainers){
                System.out.println(container);
            }
        }, false));

        menu.addOption(2, new Option("Manage containers", this::openManageContainersMenu, false));

        menu.open();
    }

    public void openManageContainersMenu(){
        Menu menu = new Menu("Manage containers");

        int i = 1;
        for(StoredContainer container : storedContainers){
            menu.addOption(i, new Option(container.getContainer().getContainerID().toString(), () -> {
                container.getContainer().openContainerMenu();
            }, true));
        }

        menu.open();
    }

    public void storeContainer(Container container) throws FullWarehouseException {
        if(storedContainers.size() >= maxContainers){
            throw new FullWarehouseException();
        }
        else{
            storedContainers.add(new StoredContainer(Main.getPortTime().getPortDate(), container));
        }
    }

    public void forceAddContainer(StoredContainer storedContainer){
        storedContainers.add(storedContainer);
    }

    public void removeContainer(Container container){
        for(StoredContainer storedContainer : storedContainers){
            if(storedContainer.getContainer().equals(container)){
                storedContainers.remove(storedContainer);
                return;
            }
        }
    }

    public List<StoredContainer> getStoredContainers() {
        return storedContainers;
    }

    public int getMaxContainers() {
        return maxContainers;
    }
}
