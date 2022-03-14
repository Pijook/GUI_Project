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
        return "Type: Liquid, Max capacity: " + maxCapacity + ", " + super.toString();
    }
}
