package com.company.train;

import com.company.container.Container;

import java.util.ArrayList;
import java.util.List;

public class Train extends Thread {

    private final List<Container> loadedContainers;
    private boolean onTheWay;

    public Train(){
        loadedContainers = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            System.out.println("Train left station!");

            onTheWay = true;
            sleep(30 * 1000);
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

    public void loadContainer(Container container){
        loadedContainers.add(container);
        if(loadedContainers.size() >= 10){
            this.start();
        }
    }

    public boolean isOnTheWay() {
        return onTheWay;
    }

    public void setOnTheWay(boolean onTheWay) {
        this.onTheWay = onTheWay;
    }
}
