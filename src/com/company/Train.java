package com.company;

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
            onTheWay = true;
            sleep(30 * 1000);
            onTheWay = false;
            loadedContainers.clear();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
