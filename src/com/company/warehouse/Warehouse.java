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
import java.time.Period;
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
        while(!isInterrupted()){
            synchronized (this){
                try{
                    wait();
                }
                catch (InterruptedException e){
                    break;
                }
            }

            //sleep(5000);

            LocalDate currentPortDate = Main.getPortTime().getPortDate();
            Iterator<StoredContainer> iterator = storedContainers.iterator();

            Train train = Main.getTrain();

            while(iterator.hasNext()){
                StoredContainer storedContainer = iterator.next();

                if(!train.isOnTheWay()){
                    Container container = storedContainer.getContainer();
                    boolean removed = false;
                    if(container instanceof Liquid && container instanceof Hazardous){
                        if(Period.between(storedContainer.getStoreDate(), currentPortDate).getDays() >= 10){
                            removed = true;
                        }
                    }
                    else if(container instanceof Hazardous && container instanceof Heavy){
                        if(Period.between(storedContainer.getStoreDate(), currentPortDate).getDays() >= 14){
                            removed = true;
                        }
                    }
                    else if(storedContainer.getContainer() instanceof Exploding){
                        if(Period.between(storedContainer.getStoreDate(), currentPortDate).getDays() >= 5){
                            removed = true;

                        }
                    }

                    if(removed){
                        train.loadContainer(storedContainer);
                        iterator.remove();
                        System.out.println("\nDangerous container with ID " + container.getContainerID() + " just got moved on to the train!\n");
                    }
                }
            }
        }

    }

    public Menu getWarehouseMenu(){
        Menu menu = new Menu("Warehouse menu");

        menu.addOption(1, new Option("Show stored containers", () -> {
            for(StoredContainer storedContainer : storedContainers){
                Container container = storedContainer.getContainer();
                String text = storedContainer.toString();

                if(container instanceof Hazardous || container instanceof Exploding){

                    LocalDate storeDate = storedContainer.getStoreDate();
                    LocalDate currentDate = Main.getPortTime().getPortDate();

                    if(container instanceof Liquid && container instanceof Hazardous){
                        storeDate = storeDate.plusDays(10);
                    }
                    else if(container instanceof Hazardous && container instanceof Heavy){
                        storeDate = storeDate.plusDays(14);
                    }
                    else if(container instanceof Exploding){
                        storeDate = storeDate.plusDays(5);
                    }

                    text = text + "\n" + "Days until utilization: " + Period.between(currentDate, storeDate).getDays();
                }
                System.out.println(text);
                System.out.println("");
            }
        }, false));

        menu.addOption(2, new Option("Manage containers", () -> {
            menu.goToMenu(getManageContainersMenu());
        }, false));

        menu.addOption(3, new Option("Create new container", () -> {
            menu.goToMenu(Main.getContainerController().getCreateContainerMenu());
        }, false));

        return menu;
    }

    public Menu getManageContainersMenu(){
        Menu menu = new Menu("Manage containers");

        int i = 1;
        for(StoredContainer container : storedContainers){
            menu.addOption(i, new Option(container.getContainer().getContainerID().toString(), () -> {
                menu.goToMenu(container.getContainer().getContainerMenu());
            }, true));
            i++;
        }

        return menu;
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

    public void addContainer(StoredContainer container){
        storedContainers.add(container);
    }
}
