package com.company.container;

import com.company.container.containerTypes.Hazardous;
import com.company.container.containerTypes.Liquid;

public class HazardousLiquidContainer extends Container implements Hazardous, Liquid {

    private double radiationLevel;
    private double maxCapacity;

    public HazardousLiquidContainer(double mass, double maxCapacity, Double radiationLevel) {
        super(mass);
        this.radiationLevel = radiationLevel;
        this.maxCapacity = maxCapacity;
    }

    @Override
    public double getRadiationLevel() {
        return radiationLevel;
    }

    @Override
    public void setRadiationLevel(double radiationLevel) {
        this.radiationLevel = radiationLevel;
    }

    @Override
    public double getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public void setMaxCapacity(double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public String toString() {
        return "type: LiquidHazardous\n" +
                "containerID: " + getContainerID() + "\n" +
                "mass: " + getMass() + "\n" +
                "maxCapacity: " + getMaxCapacity() + "\n" +
                "radiationLevel: " + getRadiationLevel() + "\n" +
                "onShip: " + getOnShip();
    }


}
