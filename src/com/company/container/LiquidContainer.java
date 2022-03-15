package com.company.container;

public class LiquidContainer extends Container {

    private double maxCapacity;

    public LiquidContainer(Double mass, double maxCapacity) {
        super(mass);
        this.maxCapacity = maxCapacity;
    }

    public double getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public String toString() {
        return "type: Liquid\n" +
                "containerID: " + getContainerID() + "\n" +
                "mass: " + getMass() + "\n" +
                "maxCapacity: " + getMaxCapacity() + "\n" +
                "onShip: " + getOnShip();
    }
}
