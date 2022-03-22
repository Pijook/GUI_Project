package com.company.container;

import com.company.container.containerTypes.Liquid;

public class LiquidContainer extends Container implements Liquid {

    private double maxCapacity;

    public LiquidContainer(double mass, double maxCapacity) {
        super(mass);
        this.maxCapacity = maxCapacity;
    }

    @Override
    public double getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public void setMaxCapacity(double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /*@Override
    public String toString() {
        return "type: Liquid\n" +
                "containerID: " + getContainerID() + "\n" +
                "mass: " + getMass() + "\n" +
                "maxCapacity: " + getMaxCapacity() + "\n" +
                "senderID: " + getSenderID() + "\n" +
                "onShip: " + getOnShip();
    }*/

    @Override
    public String toString() {
        return "type: Liquid\n" +
                "maxCapacity: " + maxCapacity + "\n" +
                super.toString();
    }
}
