package com.company.container;

import com.company.container.containerTypes.Cooling;
import com.company.container.containerTypes.Heavy;

public class CoolingContainer extends Container implements Heavy, Cooling {

    private double minVoltage;
    private String specialProtection;

    public CoolingContainer(double mass, String specialProtection, double minVoltage) {
        super(mass);
        this.specialProtection = specialProtection;
        this.minVoltage = minVoltage;
    }

    @Override
    public double getMinVoltage() {
        return minVoltage;
    }

    @Override
    public void setMinVoltage(double minVoltage) {
        this.minVoltage = minVoltage;
    }

    @Override
    public String getSpecialProtection() {
        return specialProtection;
    }

    @Override
    public void setSpecialProtection(String specialProtection) {
        this.specialProtection = specialProtection;
    }

    @Override
    public String toString() {
        return "type: Cooling\n" +
                "containerID: " + getContainerID() + "\n" +
                "mass: " + getMass() + "\n" +
                "minVoltage: " + getMinVoltage() + "\n" +
                "specialProtection: " + getSpecialProtection() + "\n" +
                "senderID: " + getSenderID() + "\n" +
                "onShip: " + getOnShip();
    }


}
