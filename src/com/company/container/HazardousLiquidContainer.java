package com.company.container;

import com.company.container.containerTypes.Hazardous;
import com.company.container.containerTypes.Heavy;
import com.company.container.containerTypes.Liquid;

public class HazardousLiquidContainer extends Container implements Heavy, Hazardous, Liquid {

    private double radiationLevel;
    private double maxCapacity;
    private String specialProtection;

    public HazardousLiquidContainer(double mass, String specialProtection, double maxCapacity, Double radiationLevel) {
        super(mass);
        this.specialProtection = specialProtection;
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
    public String getSpecialProtection() {
        return specialProtection;
    }

    @Override
    public void setSpecialProtection(String specialProtection) {
        this.specialProtection = specialProtection;
    }

    /*@Override
    public String toString() {
        return "type: LiquidHazardous\n" +
                "containerID: " + getContainerID() + "\n" +
                "mass: " + getMass() + "\n" +
                "specialProtection: " + getSpecialProtection() + "\n" +
                "maxCapacity: " + getMaxCapacity() + "\n" +
                "radiationLevel: " + getRadiationLevel() + "\n" +
                "senderID: " + getSenderID() + "\n" +
                "onShip: " + getOnShip();
    }*/

    @Override
    public String toString() {
        return "type: LiquidHazardous\n" +
                "radiationLevel: " + radiationLevel + "\n" +
                "maxCapacity: " + maxCapacity + "\n" +
                "specialProtection: " + specialProtection + "\n" +
                super.toString();
    }
}
