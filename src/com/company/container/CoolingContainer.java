package com.company.container;

public class CoolingContainer extends HeavyContainer {

    private double minVoltage;

    public CoolingContainer(Double mass, String specialProtection, double minVoltage) {
        super(mass, specialProtection);
        this.minVoltage = minVoltage;
    }

    public double getMinVoltage() {
        return minVoltage;
    }

    public void setMinVoltage(double minVoltage) {
        this.minVoltage = minVoltage;
    }
}
