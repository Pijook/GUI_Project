package com.company.warehouse;

import com.company.Main;
import com.company.container.containerTypes.Exploding;
import com.company.container.containerTypes.Hazardous;
import com.company.container.containerTypes.Heavy;
import com.company.container.containerTypes.Liquid;
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
        while(true){
            try{
                LocalDate currentPortDate = Main.getPortTime().getPortDate();
                Iterator<StoredContainer> iterator = storedContainers.iterator();

                Train train = Main.getTrain();

                while(iterator.hasNext()){
                    StoredContainer storedContainer = iterator.next();

                    if(!train.isOnTheWay()){
                        Container container = storedContainer.getContainer();
                        if(container instanceof Liquid && container instanceof Hazardous){
                            if(currentPortDate.compareTo(storedContainer.getStoreDate()) >= 10){
                                train.loadContainer(storedContainer);
                                storedContainers.remove(storedContainer);
                            }
                        }
                        else if(container instanceof Hazardous && container instanceof Heavy){
                            if(currentPortDate.compareTo(storedContainer.getStoreDate()) >= 14){
                                train.loadContainer(storedContainer);
                                storedContainers.remove(storedContainer);
                            }
                        }
                        else if(storedContainer.getContainer() instanceof Exploding){
                            if(currentPortDate.compareTo(storedContainer.getStoreDate()) >= 5){
                                train.loadContainer(storedContainer);
                                storedContainers.remove(storedContainer);
                            }
                        }
                    }
                }

                sleep(5000);
            }
            catch (InterruptedException e){
                break;
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

        menu.addOption(2, new Option("Manage containers", () -> {
            openManageContainersMenu();
        }, false));

        menu.addOption(3, new Option("Create new container", () -> {
            Main.getContainerController().openCreateContainerMenu();
        }, false));

        menu.open();
    }

    public void openManageContainersMenu(){
        Menu menu = new Menu("Manage containers");

        int i = 1;
        for(StoredContainer container : storedContainers){
            menu.addOption(i, new Option(container.getContainer().getContainerID().toString(), () -> {
                container.getContainer().openContainerMenu();
            }, true));
            i++;
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
